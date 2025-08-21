package com.browka.bankinglearning.Requests;

public class MoneyRequest {
    private String to;
    private int amount;

    // Геттеры и сеттеры
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}