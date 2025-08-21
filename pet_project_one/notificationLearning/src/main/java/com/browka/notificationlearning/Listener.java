package com.browka.notificationlearning;

import com.browka.notificationlearning.Requests.KafkaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    private final EmailService emailService;
    public Listener(EmailService emailService) {
        this.emailService = emailService;
    }
    @KafkaListener(topics="BankNotification",groupId = "notification")
    public void listen(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            KafkaRequest kafkaRequest = mapper.readValue(message, KafkaRequest.class);
            emailService.sendEmail(kafkaRequest.getEmail(), kafkaRequest.getAmount(), kafkaRequest.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
