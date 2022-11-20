package ru.sotn.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sotn.orderservice.domain.OrderLineItem;

import java.util.Optional;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {

    Optional<OrderLineItem> findOrderLineItemBySkuCode(String skuCode);
}
