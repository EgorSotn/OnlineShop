package ru.sotn.notificationservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sotn.notificationservice.model.OrderLineItemDto;
import ru.sotn.notificationservice.model.PaymentDto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext
//@EmbeddedKafka
//@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationServiceImplKafkaTest {
    private final String TOPIC_NAME = "notificationTopic";
    private Producer<String, String> producer;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private NotificationServiceImpl notificationService;
    @Captor
    ArgumentCaptor<PaymentDto> paymentDtoArgumentCaptor;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
    }

    @Test
    void sendToEmailNotification() throws JsonProcessingException {
        val paymentDto = new PaymentDto("number","email", "addrss",
                Collections.singletonList(new OrderLineItemDto("skuCode", BigDecimal.valueOf(1900l),1)), 50l);
        String uuid = "notificationId";
        String message = objectMapper.writeValueAsString(paymentDto);
        producer.send(new ProducerRecord<>(TOPIC_NAME, 0, uuid, message));
        producer.flush();

        // Read the message and assert its properties

//        verify(notificationService, timeout(10000).times(1))
//                .sendToEmailNotification(paymentDtoArgumentCaptor.capture());
//
//        PaymentDto paymentDto1 = paymentDtoArgumentCaptor.getValue();
//        assertNotNull(paymentDto1);
    }
    @AfterAll
    void shutdown() {
        producer.close();
    }
}