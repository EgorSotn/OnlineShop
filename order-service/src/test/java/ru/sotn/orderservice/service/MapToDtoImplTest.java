package ru.sotn.orderservice.service;

import lombok.val;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.domain.OrderLineItem;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(MapToDtoImpl.class)
class MapToDtoImplTest {
    @Autowired
    MapToDto mapToDto;
    private OrderDto orderDto;
    private Order order;
    private OrderLineItem orderLineItem1, orderLineItem2;
    private OrderLineItemDto orderLineItemDto1, orderLineItemDto2;
    @BeforeEach
    void init(){
        Order order1 = new Order();
        order1.setOrderNumber(UUID.randomUUID().toString());
        order1.setAddress("Москва");
        order1.setEmail("daniil");
        order1.setId(1L);

        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(BigDecimal.valueOf(1900L));
        orderLineItem.setQuantity(4);
        orderLineItem.setSkuCode("clothe1_black_XL");

        OrderLineItem orderLineItem1 = new OrderLineItem();
        orderLineItem1.setPrice(BigDecimal.valueOf(1900L));
        orderLineItem1.setQuantity(4);
        orderLineItem1.setSkuCode("clothe2_black_XL");
        order1.setOrderLineItemsList(List.of(orderLineItem1, orderLineItem));

        OrderDto orderDto = new OrderDto();
        orderDto.setEmail("daniil");
        orderDto.setAddress("Москва");

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto();
        orderLineItemDto.setSkuCode("clothe1_black_XL");
        orderLineItemDto.setQuantity(4);
        orderLineItemDto.setPrice(BigDecimal.valueOf(1900L));

        OrderLineItemDto orderLineItemDto1 = new OrderLineItemDto();
        orderLineItemDto1.setSkuCode("clothe2_black_XL");
        orderLineItemDto1.setQuantity(4);
        orderLineItemDto1.setPrice(BigDecimal.valueOf(1900L));

        List<OrderLineItemDto> clothOrderDtoList = List.of(orderLineItemDto1,orderLineItemDto);

        orderDto.setOrderLineItemsList(clothOrderDtoList);

        this.order = order1;
        this.orderDto = orderDto;
        this.orderLineItem1 = orderLineItem1;
        this.orderLineItem2 = orderLineItem2;
        this.orderLineItemDto1 = orderLineItemDto1;
        this.orderLineItemDto2 = orderLineItemDto2;
    }
    @Test
    void dtoToOrder() {
        val expectedOrder = mapToDto.dtoToOrder(orderDto);
        assertThat(expectedOrder.getOrderLineItemsList()).isEqualTo(order.getOrderLineItemsList());

    }

    @Test
    void orderToDto() {
        assertThat(mapToDto.orderToDto(order).getOrderLineItemsList()).isEqualTo(orderDto.getOrderLineItemsList());
    }

    @Test
    void dtoToOrderLineItem() {
        assertThat(mapToDto.dtoToOrderLineItem(orderLineItemDto1)).isEqualTo(orderLineItem1);
    }

    @Test
    void orderLineItemToDto() {
        assertThat(mapToDto.orderLineItemToDto(orderLineItem1)).isEqualTo(orderLineItemDto1);
    }
}