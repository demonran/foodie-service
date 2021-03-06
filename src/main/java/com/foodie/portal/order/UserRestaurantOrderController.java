package com.foodie.portal.order;

import com.foodie.portal.commons.PageCommand;
import com.foodie.portal.commons.Pagination;
import com.foodie.portal.order.command.CreateRestaurantOrderCommand;
import com.foodie.portal.order.command.OrderPayCancelCommand;
import com.foodie.portal.order.command.OrderPaySuccessCommand;
import com.foodie.portal.order.command.PayOrderCommand;
import com.foodie.portal.order.dto.OrderInfoDto;
import com.foodie.portal.order.dto.RestaurantOrderInfoDto;
import com.foodie.portal.order.model.RestaurantOrder;
import com.foodie.portal.order.representation.OrderRepresentationService;
import com.foodie.portal.order.representation.RestaurantOrderSummaryRepresentation;
import com.foodie.portal.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "（用户）订单功能")
@RestController
@RequestMapping("user/order/restaurant")
public class UserRestaurantOrderController {

    @Autowired
    private RestaurantOrderApplicationService orderApplicationService;
    @Autowired
    private OrderRepresentationService orderRepresentationService;


    @ApiOperation("用户餐馆下单")
    @PostMapping
    public RestaurantOrder createRestaurantOrder(@RequestBody CreateRestaurantOrderCommand command) {
        var user = (User) SecurityUtils.getSubject().getPrincipal();
        return orderApplicationService.create(command, user);
    }

    @ApiOperation("我的餐馆订单列表")
    @GetMapping("list")
    public Pagination<RestaurantOrderSummaryRepresentation> restaurantOrders(PageCommand command) {
        var user = (User) SecurityUtils.getSubject().getPrincipal();
        return orderRepresentationService.myRestaurantOrderList(command.getPage(), command.getSize(), user);
    }

    @ApiOperation("用户付款")
    @PostMapping("/{id}/payment")
    public String pay(@PathVariable(name = "id") String id, @RequestBody @Valid PayOrderCommand command) {
        var user = (User) SecurityUtils.getSubject().getPrincipal();
        return orderApplicationService.prePay(id, command);
    }

    @ApiOperation("支付成功")
    @PostMapping("pay/success")
    public RestaurantOrderInfoDto successPay(@RequestBody OrderPaySuccessCommand command){
        return RestaurantOrderInfoDto.from(orderApplicationService.pay(command.getPaymentId(), command.getPayerId()));
    }

    @ApiOperation("支付取消")
    @PostMapping("pay/cancel")
    public RestaurantOrderInfoDto cancelPay(@RequestBody OrderPayCancelCommand command){
        return RestaurantOrderInfoDto.from(orderApplicationService.cancel(command.getOrderNo()));
    }

}
