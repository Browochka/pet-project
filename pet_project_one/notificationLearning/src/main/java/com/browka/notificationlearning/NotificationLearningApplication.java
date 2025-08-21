package com.browka.notificationlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class NotificationLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationLearningApplication.class, args);
    }

}
