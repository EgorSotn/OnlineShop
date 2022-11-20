package ru.sotn.orderservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sotn.orderservice.domain.OrderLineItem;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class OrderLineItemDto implements Serializable {

    private  String skuCode;
    private  BigDecimal price;
    private  Integer quantity;

}