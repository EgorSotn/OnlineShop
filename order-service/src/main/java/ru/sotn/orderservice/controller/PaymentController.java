package ru.sotn.orderservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.sotn.orderservice.controller.dto.OrderDto;
import ru.sotn.orderservice.service.PaymentService;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto startPay(@RequestParam String email) throws Exception {
        return paymentService.sendTransactionTopic(email);
    }
}
