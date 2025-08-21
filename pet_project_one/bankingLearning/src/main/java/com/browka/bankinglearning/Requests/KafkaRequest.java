package com.browka.bankinglearning.Requests;

public class KafkaRequest {
    private String username;
    private String email;
    private int amount;

    public KafkaRequest(String username, String mail, int amount) {
        this.username = username;
        this.email = mail;
        this.amount = amount;
    }
    public KafkaRequest() {}

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAmount() {
        return amount;
    }
}
