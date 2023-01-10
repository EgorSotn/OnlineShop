package ru.sotn.transactionservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Charge;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.client.OrderTransactionClient;


import ru.sotn.transactionservice.model.dto.CurrencyResponse;
import ru.sotn.transactionservice.model.dto.OrderDto;
import ru.sotn.transactionservice.model.dto.OrderLineItemDto;
import ru.sotn.transactionservice.model.dto.PaymentDto;
import ru.sotn.transactionservice.stripe.StripeClient;

import java.math.BigDecimal;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
//@ExtendWith(ReuseKafkaContainerExtension.class)
class TransactionServiceKafkaSendTest {
//    @ReusableKafkaContainer
//    KafkaContainer kafka;
    private BlockingQueue<ConsumerRecord<String, String>> records;

    private KafkaMessageListenerContainer<String, String> container;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceKafkaSendTest.class);
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderTransactionClient orderTransactionClient;

    @MockBean
    private StripeClient stripeClient;

    @MockBean
    private ConvertCurrency convertCurrency;

    @Autowired
    TransactionService transactionService;

    @BeforeAll
    void setUp() {
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(getConsumerProperties());
        ContainerProperties containerProperties = new ContainerProperties("notificationTopic");
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) record-> {
            LOGGER.debug("Listened message='{}'", record.toString());

            records.add(record);
        });
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }
    @Test
    void testKafka() throws Exception {

        Mono<OrderDto> orderDto = Mono.just(new OrderDto("number", "email", "address",
                Collections.singletonList(new OrderLineItemDto("skuCode", BigDecimal.valueOf(1900l),2))));
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setInfo(new CurrencyResponse.Info("2e234", "3"));
        when(orderTransactionClient.getOrderInTransaction(any())).thenReturn(orderDto);
        when(convertCurrency.convertCurrency(any(), any(), any())).thenReturn(Mono.just(currencyResponse));
        when(stripeClient.chargeNewCard("token",1900L)).thenReturn(new Charge());

        transactionService.charge("email", "token");

        // Read the message (John Wick user) with a test consumer from Kafka and assert its properties
        ConsumerRecord<String, String> message = records.poll(10000, TimeUnit.MILLISECONDS);
        assertNotNull(message);
        PaymentDto result = objectMapper.readValue(message.value(), PaymentDto.class);
        assertNotNull(result);
    }


    private Map<String, Object> getConsumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
                ConsumerConfig.GROUP_ID_CONFIG, "notificationId",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true",
                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10",
                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }
    @AfterAll
    void tearDown() {
        container.stop();
    }
}