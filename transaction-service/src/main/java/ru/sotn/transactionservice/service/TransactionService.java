package ru.sotn.transactionservice.service;

import ru.sotn.transactionservice.model.dto.PaymentDto;

public interface TransactionService {
    PaymentDto charge(String email, String token) throws Exception;
}
