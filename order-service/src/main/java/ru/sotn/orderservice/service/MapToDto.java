package ru.sotn.orderservice.service;

import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.domain.OrderLineItem;

public interface MapToDto {
    Order dtoToOrder(OrderDto orderDto);
    OrderDto orderToDto(Order order);
    OrderLineItem dtoToOrderLineItem(OrderLineItemDto orderLineItemsDto);
    OrderLineItemDto orderLineItemToDto(OrderLineItem orderLineItem);
}
