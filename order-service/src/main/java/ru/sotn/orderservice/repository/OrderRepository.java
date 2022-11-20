package ru.sotn.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sotn.orderservice.domain.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByEmail(String email);
    Optional<Order> findByOrderNumber(String orderNumber);
}
