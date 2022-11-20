package ru.sotn.orderservice.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.domain.OrderLineItem;
import ru.sotn.orderservice.repository.OrderLineItemRepository;
import ru.sotn.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@Import(OrderServiceImpl.class)
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderLineItemRepository OrderLineItemRepository;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private MapToDto mapToDto;

    @Test
    void getAllOrder() {
    }

    @Test
    void placeOrder() {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setAddress("Москва");
        order.setEmail("daniil");
        order.setId(1L);
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(BigDecimal.valueOf(1900L));
        orderLineItem.setQuantity(4);
        orderLineItem.setSkuCode("clothe1_black_XL");
        order.setOrderLineItemsList(Collections.singletonList(orderLineItem));

        OrderDto orderDto = new OrderDto();
        orderDto.setEmail("daniil");
        orderDto.setAddress("Москва");
        OrderLineItemDto orderLineItemDto = new OrderLineItemDto();
        orderLineItemDto.setSkuCode("clothe1_black_XL");
        orderLineItemDto.setQuantity(4);
        orderLineItemDto.setPrice(BigDecimal.valueOf(1900L));
        orderDto.setOrderLineItemsList(Collections.singletonList(orderLineItemDto));

        when(orderRepository.findByOrderNumber(any())).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);

        assertThat(orderService.placeOrder(orderDto).getOrderNumber()).isEqualTo(order.getOrderNumber());
    }

    @Test
    void addNewClothe() {
    }

    @Test
    void removeClothe() {
    }

    @Test
    void addOneQuantityInClothe() {
    }
}