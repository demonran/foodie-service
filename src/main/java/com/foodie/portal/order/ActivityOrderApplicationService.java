package com.foodie.portal.order;

import com.foodie.portal.activity.ActivityApplicationService;
import com.foodie.portal.activity.model.Activity;
import com.foodie.portal.commons.ErrorCode;
import com.foodie.portal.commons.EventPublisher;
import com.foodie.portal.commons.RestException;
import com.foodie.portal.commons.event.*;
import com.foodie.portal.order.command.CreateOrderCommand;
import com.foodie.portal.order.command.PayOrderCommand;
import com.foodie.portal.order.model.Order;
import com.foodie.portal.payment.PaymentApplicationService;
import com.foodie.portal.payment.PaypalPaymentIntent;
import com.foodie.portal.payment.PaypalPaymentMethod;
import com.foodie.portal.user.model.Merchant;
import com.foodie.portal.user.model.User;
import com.google.common.base.Stopwatch;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ActivityOrderApplicationService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ActivityApplicationService activityApplicationService;
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private PaymentApplicationService paymentApplicationService;

    @Transactional
    public Order create(CreateOrderCommand command, User user) {

        Activity activity = activityApplicationService.findById(command.getActivityId());
        //更新预定人数
        activityApplicationService.updateReserve(command.getActivityId(), command.getServiceDate(), command.getStartTime(), command.getCount());
        //创建订单
        var order = Order.create(activity, command.getCount(), command.getServiceDate(),
                command.getStartTime(), command.getOrderInfo());
        order.setUser(user);
        orderRepository.save(order);
        return order;
    }



    public String prePay(String id, @Valid PayOrderCommand command) {
        var order = orderRepository.findActivityOrderById(id);
        order.prePay(command.getPaidPrice());
        Payment payment;
        try {

            Stopwatch stopwatch = Stopwatch.createStarted();
            payment = paymentApplicationService.createActivityPayment(command.getPaidPrice(),
                    "USD", PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale, "订单支付", order.getNumber());
            stopwatch.stop();
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            log.info("开始创建活动支付:{}, cost:{}", id, elapsed);
        } catch (PayPalRESTException e) {
            throw new RestException(ErrorCode.FAILED, "支付失败");
        }
        order.setPaymentId(payment.getId());
        orderRepository.save(order);

        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return links.getHref();
            }
        }
        throw new RestException(ErrorCode.FAILED, "没有获取到支付URL");
    }



    public Order accept(String id, Merchant merchant) {
        var order = orderRepository.findActivityOrderById(id);
        order.accept(merchant);
        orderRepository.save(order);

        eventPublisher.publish(new OrderAcceptedEvent(order));
        return order;
    }

    public Order reject(String id, String reason, Merchant merchant) {
        var order = orderRepository.findActivityOrderById(id);
        order.reject(reason, merchant);
        orderRepository.save(order);

        paymentApplicationService.refundSale(order.getPaymentId(), order.getPrice(), "USD", reason);
        eventPublisher.publish(new OrderRejectedEvent(order));
        return order;
    }

    public Order startService(String id, String payNo, Merchant merchant) {
        var order = orderRepository.findActivityOrderById(id);
        order.startService(payNo, merchant);
        orderRepository.save(order);
        return order;
    }

    public Order endService(String id, Merchant merchant) {
        var order = orderRepository.findActivityOrderById(id);
        order.endService(merchant);
        orderRepository.save(order);
        eventPublisher.publish(new OrderFinishedEvent(order));
        return order;
    }

    public Order pay(String paymentId, String payerId) {
        log.info("支付回调：paymentId: {}, payerId: {}", paymentId, payerId);
        try {
            Payment payment = paymentApplicationService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                var order = orderRepository.byPaymentId(paymentId);
                order.pay();
                orderRepository.save(order);
                eventPublisher.publish(new ActivityOrderPaidEvent(order));
                return order;
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        throw new RestException(ErrorCode.FAILED, "支付失败");
    }


    public Order cancel(String orderNo) {
        var order = orderRepository.findActivityOrderByOrderNo(orderNo);
        order.cancel();
        orderRepository.save(order);
        return order;
    }


}
