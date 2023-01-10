package ru.sotn.transactionservice.service;

import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.model.dto.CurrencyResponse;

public interface ConvertCurrency {
     Mono<CurrencyResponse> convertCurrency(String to, String from, String amount);
}
