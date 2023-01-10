package ru.sotn.transactionservice.controller;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.sotn.transactionservice.model.dto.OrderLineItemDto;
import ru.sotn.transactionservice.model.dto.PaymentDto;
import ru.sotn.transactionservice.service.TransactionService;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TransactionOrderController.class)
@ActiveProfiles("test")
class TransactionOrderControllerTest {
    @MockBean
    TransactionService transactionService;
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
    void startTransaction() throws Exception {
        val paymentDto = new PaymentDto("number","email", "addrss",
                Collections.singletonList(new OrderLineItemDto("skuCode", BigDecimal.valueOf(1900l),1)), 50l);
        paymentDto.setAmount(1000l);
        when(transactionService.charge("email", "token")).thenReturn(paymentDto);
        webTestClient.mutateWith(mockOpaqueToken().authorities(new SimpleGrantedAuthority("USER")))
                .mutateWith(csrf())
                .post()
                .uri(uriBuilder -> uriBuilder.path("/api/charge").queryParam("email", "email")
                        .build())
                .header("token", "token")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PaymentDto.class)
                .value(paymentActual -> {
                    assertThat(paymentActual).isEqualTo(paymentDto);
                });

    }
}