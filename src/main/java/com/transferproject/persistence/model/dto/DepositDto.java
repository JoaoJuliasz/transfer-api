package com.transferproject.persistence.model.dto;

public class DepositDto {

    private String receiver;
    private double value;

    public DepositDto(String receiver, double value) {
        this.receiver = receiver;
        this.value = value;
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
