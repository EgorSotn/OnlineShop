package ru.sotn.orderservice.service;

import org.springframework.stereotype.Service;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.domain.OrderLineItem;

import java.util.stream.Collectors;

@Service
public class MapToDtoImpl implements MapToDto{
    @Override
    public Order dtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setEmail(orderDto.getEmail());
        order.setAddress(orderDto.getAddress());
        order.setOrderLineItemsList(orderDto
                .getOrderLineItemsList().stream().map(this::dtoToOrderLineItem).collect(Collectors.toList()));
        return order;
    }

    @Override
    public OrderDto orderToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setEmail(order.getEmail());
        orderDto.setAddress(order.getAddress());
        orderDto.setOrderLineItemsList(order
                .getOrderLineItemsList().stream().map(this::orderLineItemToDto).collect(Collectors.toList()));


        return orderDto;
    }
    @Override
    public OrderLineItem dtoToOrderLineItem(OrderLineItemDto orderLineItemsDto) {
        OrderLineItem orderLineItems = new OrderLineItem();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
    @Override
    public OrderLineItemDto orderLineItemToDto(OrderLineItem orderLineItem) {
        OrderLineItemDto orderLineItemDto = new OrderLineItemDto();
        orderLineItemDto.setPrice(orderLineItem.getPrice());
        orderLineItemDto.setQuantity(orderLineItem.getQuantity());
        orderLineItemDto.setSkuCode(orderLineItem.getSkuCode());
        return orderLineItemDto;
    }
}
