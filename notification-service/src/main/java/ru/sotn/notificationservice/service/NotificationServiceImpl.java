package ru.sotn.notificationservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.sotn.notificationservice.config.AppProps;
import ru.sotn.notificationservice.model.OrderLineItemDto;
import ru.sotn.notificationservice.model.PaymentDto;

import java.util.stream.Collectors;

import static ru.sotn.notificationservice.config.ConstKafka.NAME_TOPIC_NOTIFICATION;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{
    private final AppProps appProps;
    public final JavaMailSender emailSender;
    @Override
    @KafkaListener(topics = NAME_TOPIC_NOTIFICATION)
    public void sendToEmailNotification(PaymentDto paymentDto) {
       SimpleMailMessage message =  new SimpleMailMessage();
       message.setFrom(appProps.getUsername());
       message.setTo(paymentDto.getEmailUser());
       message.setSubject("Your order " + paymentDto.getOrderNumber() + "  is closed" );
       message.setText(paymentDto.getOrderLineItemDtos().stream()
               .map(OrderLineItemDto::toString).collect(Collectors.joining(",")) +
               "\n" +"order will be sent to: " + paymentDto.getAddress());
       emailSender.send(message);
       log.info("send gmail");
    }
}
