package ru.sotn.orderservice.service;

import ru.sotn.orderservice.controller.dto.OrderDto;

public interface PaymentService {
    OrderDto sendTransactionTopic(String email) throws Exception;
}
