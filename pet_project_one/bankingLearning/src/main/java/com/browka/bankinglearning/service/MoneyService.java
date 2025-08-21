package com.browka.bankinglearning.service;

import com.browka.bankinglearning.Requests.KafkaRequest;
import com.browka.bankinglearning.model.BankUser;
import com.browka.bankinglearning.repository.BankRepo;
import com.browka.bankinglearning.repository.MailRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MoneyService {
    @Autowired
    private BankRepo bankRepo;
    @Autowired
    private MailRepo mailRepo;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    public int checkBalance(String username) {

        return bankRepo.findByUsername(username).getBalance();
    }

    public String transfer(String username, String to, int amount) {
        BankUser fromUser = bankRepo.findByUsername(username);
        BankUser toUser = bankRepo.findByUsername(to);
        if (fromUser.getBalance() < amount) {
            return "Fail. You don't have enough money to transfer.";
        }
        fromUser.setBalance(fromUser.getBalance()-amount);
        bankRepo.save(fromUser);
        toUser.setBalance(toUser.getBalance()+amount);
        bankRepo.save(toUser);
        if (fromUser.getMail()==null) {        return "Transfer successful.Your balance is " + fromUser.getBalance();
        } else {
            String mail = mailRepo.findById(fromUser.getId()).getEmail();
            KafkaRequest request = new KafkaRequest(username,mail,amount);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String obj = mapper.writeValueAsString(request);
                kafkaTemplate.send("BankNotification",obj);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return "Trying to send message on your email ";

        }
    }
    public String DepositMoney(String username, int amount) {
        try {
            BankUser fromUser = bankRepo.findByUsername(username);
            fromUser.setBalance(fromUser.getBalance()+amount);
            bankRepo.save(fromUser);
            return "Deposit successful. " + fromUser.getUsername() + "balance is " + fromUser.getBalance();
        } catch (Exception e) {
            return "Deposit failed.";
        }
    }
}
