package ru.sotn.orderservice.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public OrderLineItem(String skuCode, BigDecimal price, Integer quantity) {
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }
}
