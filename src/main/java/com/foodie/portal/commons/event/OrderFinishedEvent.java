package com.foodie.portal.commons.event;

import com.foodie.portal.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderFinishedEvent extends DomainEvent{
    private Order order;
}
