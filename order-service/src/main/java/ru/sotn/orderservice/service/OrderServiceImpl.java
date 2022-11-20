package ru.sotn.orderservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.domain.OrderLineItem;
import ru.sotn.orderservice.exception.NotFoundException;
import ru.sotn.orderservice.repository.OrderLineItemRepository;
import ru.sotn.orderservice.repository.OrderRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final MapToDto mapToDto;

    @Override
    public List<Order> getAllOrder() {
        var o = orderRepository.findAll();
        return orderRepository.findAll();
    }

    @Transactional
    public Order placeOrder(OrderDto orderDto){
        Optional<Order> findOrder = orderRepository.findByOrderNumber(orderDto.getOrderNumber());
        if(findOrder.isPresent()){
            findOrder.get().setOrderNumber(orderDto.getOrderNumber());
            findOrder.get().setEmail(orderDto.getEmail());
            findOrder.get().setAddress(orderDto.getAddress());
            findOrder.get().setOrderLineItemsList(orderDto.getOrderLineItemsList()
                    .stream().map(mapToDto::dtoToOrderLineItem).collect(Collectors.toList()));
            return orderRepository.save(findOrder.get());
        }else {
            orderDto.setOrderNumber(UUID.randomUUID().toString());
            return orderRepository.save( mapToDto.dtoToOrder(orderDto));
        }
//        Order order = new Order();
//        order.setOrderNumber(UUID.randomUUID().toString());
//
//        List<OrderLineItem> orderLineItems = orderDto.getOrderLineItemsList()
//                .stream()
//                .map(mapToDto::mapToDto)
//                .toList();
//
//        order.setOrderLineItemsList(orderLineItems);

    }
    @Transactional
    @Override
    public Order addNewClothe(OrderLineItemDto orderLineItemDto, String email) {
        Order order = orderRepository.findOrderByEmail(email).orElseThrow(()->new NotFoundException(email));
        order.getOrderLineItemsList().add(mapToDto.dtoToOrderLineItem(orderLineItemDto));
        Order orderLineItem = orderRepository.save(order);
        log.info("orderItem {} is saved", orderLineItem.getId());
        return order;

    }

    @Transactional
    @Override
    public Order removeClothe(String skuCode, String email) {
        Order order = orderRepository.findOrderByEmail(email).orElseThrow(()->new NotFoundException(email));
        OrderLineItem findOrderLineItem = orderLineItemRepository
                .findOrderLineItemBySkuCode(skuCode).orElseThrow(()->new NotFoundException(skuCode));
        if(findOrderLineItem.getQuantity()>0){
            order.getOrderLineItemsList().forEach(o->{
                if(o.equals(findOrderLineItem)){
                    o.setQuantity(o.getQuantity()-1);
                }
            });
            Order orderSave = orderRepository.save(order);
            log.info("orderItem {} is remove 1 item", findOrderLineItem.getSkuCode());
            return orderSave;
        }
        else {
//         order.getOrderLineItemsList().forEach(o->{
//            if(o.equals(orderLineItem)){
//              orderLineItemRepository.delete(o);
//
//            }
//         });
            order.setOrderLineItemsList(order.getOrderLineItemsList().stream()
                    .filter(o->!(o.getSkuCode().equals(skuCode))).collect(Collectors.toList()));
            orderLineItemRepository.delete(findOrderLineItem);
            Order orderSave = orderRepository.save(order);
//         orderLineItemRepository.delete(orderLineItem);
            log.info("orderItem {} is delete", findOrderLineItem.getSkuCode());
            return orderSave;
        }

    }

    @Transactional
    @Override
    public Order addOneQuantityInClothe(String skuCode, String email) {
        Order order = orderRepository.findOrderByEmail(email).orElseThrow(()->new NotFoundException(email));
        OrderLineItem orderLineItem = orderLineItemRepository
                .findOrderLineItemBySkuCode(skuCode).orElseThrow(()->new NotFoundException(skuCode));

        order.getOrderLineItemsList().forEach(o->{
            if(o.equals(orderLineItem)){
                o.setQuantity(o.getQuantity()+1);
            }
        });

//      orderLineItem.setQuantity(orderLineItem.getQuantity()+1);
//      OrderLineItem orderLineItemSave = orderLineItemRepository.save(orderLineItem);
        Order orderSave  = orderRepository.save(order);
        log.info("orderItem {} add 1 item", orderLineItem.getSkuCode());
        return orderSave;
    }



}
