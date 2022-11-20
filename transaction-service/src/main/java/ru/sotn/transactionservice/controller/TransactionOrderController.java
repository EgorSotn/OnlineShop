package ru.sotn.transactionservice.controller;


import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.model.PaymentDto;
import ru.sotn.transactionservice.service.TransactionService;


import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class TransactionOrderController {
    private final TransactionService transactionService;


    @PostMapping("/api/charge")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaymentDto> startTransaction(@RequestParam(name = "email") String email, ServerHttpRequest request){


        HttpHeaders headers = request.getHeaders();
        val token = headers.get("token");

        CompletableFuture<PaymentDto> future =
                CompletableFuture.supplyAsync(()->
                {
                    try {
                        PaymentDto paymentDto;
                        if(token == null){
                            paymentDto = transactionService.charge(email, null);
                        }
                        else {
                            paymentDto = transactionService.charge(email, token.get(0));
                        }
                        return paymentDto;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        Mono<PaymentDto> chargeMono = Mono.fromFuture(future);
        return chargeMono;
    }
}
