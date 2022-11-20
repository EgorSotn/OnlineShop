package ru.sotn.orderservice.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "t_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @Column(unique = true)
    private String email;
    private String address;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderLineItem> orderLineItemsList = new ArrayList<>();
}