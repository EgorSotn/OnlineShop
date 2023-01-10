package ru.sotn.transactionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class OrderDto implements Serializable {

    private  String orderNumber;
    private  String email;
    private  String address;
    private  List<OrderLineItemDto> orderLineItemsList = new ArrayList<>();



}