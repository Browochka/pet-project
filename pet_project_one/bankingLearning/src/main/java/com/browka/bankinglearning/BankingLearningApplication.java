package com.browka.bankinglearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.kafka.annotation.EnableKafka;

@EntityScan
@EnableKafka
@SpringBootApplication
public class BankingLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingLearningApplication.class, args);
    }

}
