package com.browka.bankinglearning.controller;

import com.browka.bankinglearning.Requests.DepositRequest;
import com.browka.bankinglearning.Requests.KafkaRequest;
import com.browka.bankinglearning.Requests.MoneyRequest;
import com.browka.bankinglearning.Throws.SelfTransferException;
import com.browka.bankinglearning.service.MoneyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoneyController {
    @Autowired
    private MoneyService moneyService;
    @GetMapping("/balance")
    public int balance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return moneyService.checkBalance(username);
    }
    @PostMapping("transfer")
    public String transfermoney(@RequestBody MoneyRequest transferRequest) throws SelfTransferException {// возвращает баланс пользователя после перевода
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(username.equals(transferRequest.getTo())) {
            throw new SelfTransferException();
        }
        return moneyService.transfer(username,transferRequest.getTo(),transferRequest.getAmount());
    }
    @PostMapping("/deposit")
    @PreAuthorize("hasRole('ADMIN')")
    public String deposit(@RequestBody DepositRequest depositRequest) {

        return moneyService.DepositMoney(depositRequest.getUsername(),depositRequest.getAmount());
    }

}
