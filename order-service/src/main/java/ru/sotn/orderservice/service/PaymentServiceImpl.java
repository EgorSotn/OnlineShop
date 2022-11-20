package ru.sotn.orderservice.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.exception.NotFoundException;
import ru.sotn.orderservice.repository.OrderRepository;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
//    private final KafkaTemplate<Long, OrderDto> kafkaTemplate;

    private final MapToDto mapToDto;

    private final OrderRepository orderRepository;
    @Override
    public OrderDto sendTransactionTopic(String email) throws Exception {
        Order order = orderRepository.findOrderByEmail(email).orElseThrow(
                ()->new NotFoundException(email));
        OrderDto orderDto =  mapToDto.orderToDto(order);

        orderRepository.delete(order);
        return orderDto;
    }
}
