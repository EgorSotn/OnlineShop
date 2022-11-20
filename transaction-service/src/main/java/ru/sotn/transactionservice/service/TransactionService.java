package ru.sotn.transactionservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import ru.sotn.transactionservice.model.OrderDto;
import ru.sotn.transactionservice.model.PaymentDto;

public interface TransactionService {
    PaymentDto charge(String email, String token) throws Exception;
}
