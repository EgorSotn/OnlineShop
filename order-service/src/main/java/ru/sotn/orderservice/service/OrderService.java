package ru.sotn.orderservice.service;


import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    Order placeOrder(OrderDto orderDto);
    Order addNewClothe(OrderLineItemDto orderLineItemDto, String email);
    Order removeClothe(String skuCode, String email);
    Order addOneQuantityInClothe(String skuCode, String email);

}
