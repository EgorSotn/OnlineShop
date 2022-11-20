package ru.sotn.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String orderNumber;
    private String emailUser;
    private String address;
    private Collection<OrderLineItemDto> orderLineItemDtos = new ArrayList<>();
    private Long amount;
}
