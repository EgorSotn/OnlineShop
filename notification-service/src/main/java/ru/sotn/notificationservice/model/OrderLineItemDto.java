package ru.sotn.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class OrderLineItemDto implements Serializable {

    private  String skuCode;
    private  BigDecimal price;
    private  Integer quantity;

}