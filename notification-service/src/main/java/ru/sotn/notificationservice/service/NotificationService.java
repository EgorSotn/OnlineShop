package ru.sotn.notificationservice.service;

import ru.sotn.notificationservice.model.PaymentDto;

public interface NotificationService {
    void sendToEmailNotification(PaymentDto paymentDto);
}
