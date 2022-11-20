package ru.sotn.transactionservice.service;


import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.config.OrderUtil;
import ru.sotn.transactionservice.client.OrderTransactionClient;

import ru.sotn.transactionservice.model.CurrencyResponse;
import ru.sotn.transactionservice.model.OrderDto;
import ru.sotn.transactionservice.model.PaymentDto;
import ru.sotn.transactionservice.stripe.StripeClient;


import static ru.sotn.transactionservice.config.ConstKafka.NAME_TOPIC_CONSUMER_NOTIFICATION;



@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceKafkaSend implements TransactionService{

    private final KafkaTemplate<Long, PaymentDto> kafkaTemplate;
    private final OrderTransactionClient orderTransactionClient;
    private final StripeClient stripeClient;
    private final ConvertCurrency convertCurrency;


    @Override
    public PaymentDto charge(String email,String token) throws Exception {

        OrderDto orderDto = orderTransactionClient.getOrderInTransaction(email).block();
        Long amount = OrderUtil.calculateOrderAmountInCents(orderDto);
        Mono<CurrencyResponse> currencyResponse = convertCurrency.convertCurrency("USD", "RUB", amount.toString());
        Charge charge = new Charge();
        Double usd = Double.parseDouble(currencyResponse.map(CurrencyResponse::getInfo).map(CurrencyResponse.Info::getQuote).block());
        if(token != null){
             charge = stripeClient.chargeNewCard(token, amount*
                     Double.parseDouble(currencyResponse.block().getInfo().getQuote()));
             log.info("token: {} accept with front", token);
        }

        PaymentDto paymentDto = new PaymentDto(orderDto.getOrderNumber(),orderDto.getEmail(),
                orderDto.getAddress(),orderDto.getOrderLineItemsList(),
                (long) (amount*usd));

        kafkaTemplate.send(NAME_TOPIC_CONSUMER_NOTIFICATION, paymentDto);
        log.info("kafa send massage: {}", orderDto.getEmail());
        return paymentDto;
    }

}
