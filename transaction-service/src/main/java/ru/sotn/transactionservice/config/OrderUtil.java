package ru.sotn.transactionservice.config;


import org.springframework.stereotype.Component;
import ru.sotn.transactionservice.model.dto.OrderDto;
import ru.sotn.transactionservice.model.dto.OrderLineItemDto;

import java.math.BigDecimal;

@Component
public class OrderUtil {
    public static Long calculateOrderAmountInCents(OrderDto orderDto){
        BigDecimal amount = new BigDecimal(0);

        if (orderDto != null && orderDto.getOrderLineItemsList() != null) {
            for (OrderLineItemDto orderLineItemDto:orderDto.getOrderLineItemsList()) {
                if(orderLineItemDto.getQuantity()>0){
                    orderLineItemDto.getQuantity();
                    BigDecimal price = orderLineItemDto.getPrice().multiply(BigDecimal.valueOf(orderLineItemDto.getQuantity()));

                   amount = amount.add(price);
                }
            }

        }



        return amount.longValue();
    }
}
