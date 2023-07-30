package com.transferproject.persistence.model;

public class Deposit {

    private String sender;
    private String receiver;
    private double value;

    public Deposit(String sender, String receiver, double value) {
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
