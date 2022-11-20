package ru.sotn.orderservice.controller;


import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import ru.sotn.orderservice.config.SecurityConfig;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.controller.dto.OrderLineItemDto;
import ru.sotn.orderservice.domain.Order;
import ru.sotn.orderservice.domain.OrderLineItem;
import ru.sotn.orderservice.service.MapToDto;
import ru.sotn.orderservice.service.OrderService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {

    @MockBean
    private OrderService orderService;
    @MockBean
    private MapToDto mapToDto;

    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext applicationContext;



    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient
                .bindToApplicationContext(applicationContext)
                .apply(springSecurity())
                .build();
    }
    @Test
    void shouldPlaceOrder() {
        val order = new Order(1l, UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItem("tshirt_black_XL", BigDecimal.valueOf( 100),2)));
        val orderDto = new OrderDto(UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItemDto("tshirt_black_XL", BigDecimal.valueOf( 100), 2)));
        when(orderService.addNewClothe(any(), any())).thenReturn(order);
        when(mapToDto.orderToDto(any())).thenReturn(orderDto);

        webTestClient.mutateWith(mockOpaqueToken().authorities(new SimpleGrantedAuthority("ADMIN")))
                .mutateWith(csrf())
                .post()
                .uri(uriBuilder -> uriBuilder.path("/api/order")
                        .build())
                .body(Mono.just(orderDto), OrderDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderDto.class)
                .value(oDto->{
                    assertThat(oDto.getOrderLineItemsList().get(0).getQuantity()).isEqualTo(orderDto.getOrderLineItemsList().get(0).getQuantity());
                });
    }


    @Test
    void shouldCreateNewClothe() {
        val order = new Order(1l, UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItem("tshirt_black_XL", BigDecimal.valueOf( 100),2)));
        val orderLineItemDto =  new OrderLineItemDto("tshirt_black_XL", BigDecimal.valueOf( 100), 2);
        val orderDto = new OrderDto(UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
               orderLineItemDto));
        when(orderService.addNewClothe(any(), any())).thenReturn(order);
        when(mapToDto.orderToDto(any())).thenReturn(orderDto);

        webTestClient.mutateWith(mockOpaqueToken().authorities(new SimpleGrantedAuthority("ADMIN")))
                .mutateWith(csrf())
                .post()
                .uri(uriBuilder -> uriBuilder.path("/api/order/item")
                        .queryParam("email", "email")
                        .build())
                .body(Mono.just(orderLineItemDto), OrderLineItemDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderDto.class)
                .value(oDto->{
                    assertThat(oDto.getOrderLineItemsList().get(0).getQuantity()).isEqualTo(orderDto.getOrderLineItemsList().get(0).getQuantity());
                });
    }

    @Test
    void shouldRemoveClothe() {
        val order = new Order(1l, UUID.randomUUID().toString(),
            "email", "address", Collections.singletonList(
            new OrderLineItem("tshirt_black_XL", BigDecimal.valueOf( 100),2)));
        val orderDto = new OrderDto(UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItemDto("tshirt_black_XL", BigDecimal.valueOf( 100), 2)));
        when(orderService.removeClothe(any(), any())).thenReturn(order);
        when(mapToDto.orderToDto(any())).thenReturn(orderDto);

        webTestClient.mutateWith(mockOpaqueToken().authorities(new SimpleGrantedAuthority("ADMIN")))
                .mutateWith(csrf())
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/api/order/item/delete")
                        .queryParam("sku_code", "tshirt_black_XL")
                        .queryParam("email", "email")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderDto.class)
                .value(oDto->{
                    assertThat(oDto.getOrderLineItemsList().get(0).getQuantity()).isEqualTo(orderDto.getOrderLineItemsList().get(0).getQuantity());
                });
    }

    @Test
    void shouldAddQueQuantityInClothe() {
        val order = new Order(1l, UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItem("tshirt_black_XL", BigDecimal.valueOf( 100),2)));
        val orderDto = new OrderDto(UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItemDto("tshirt_black_XL", BigDecimal.valueOf( 100), 2)));


        when(orderService.addOneQuantityInClothe(any(), any())).thenReturn(order);
        when(mapToDto.orderToDto(any())).thenReturn(orderDto);
        webTestClient.mutateWith(mockOpaqueToken().authorities(new SimpleGrantedAuthority("USER")))
                .mutateWith(csrf())
                .put()
                .uri(uriBuilder -> uriBuilder.path("/api/order/item/add")
                        .queryParam("sku_code", "tshirt_black_XL")
                        .queryParam("email", "email")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderDto.class)
                .value(oDto->{
                    assertThat(oDto.getOrderLineItemsList().get(0).getQuantity())
                            .isEqualTo(orderDto.getOrderLineItemsList().get(0).getQuantity());
                });

    }

    @Test
    void shouldGetAllOrder() {
        val orders = Collections.singletonList(new Order(1l, UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                        new OrderLineItem("tshirt_black_XL", BigDecimal.valueOf( 100),2))));
        val orderDtos = Collections.singletonList(new OrderDto(UUID.randomUUID().toString(),
                "email", "address", Collections.singletonList(
                new OrderLineItemDto("tshirt_black_XL", BigDecimal.valueOf( 100), 2))));

        when(orderService.getAllOrder()).thenReturn(orders);
        when(mapToDto.orderToDto(any())).thenReturn(orderDtos.get(0));
        webTestClient.mutateWith(mockOpaqueToken())
                .get()
                .uri("/api/order")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OrderDto.class)
                .value(val->{
                    assertThat(val.get(0).getEmail()).isEqualTo(orderDtos.get(0).getEmail());
                });
    }

}