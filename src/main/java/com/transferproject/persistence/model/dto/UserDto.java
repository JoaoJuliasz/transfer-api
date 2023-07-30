package com.transferproject.persistence.model.dto;

import com.transferproject.persistence.model.User;
import com.transferproject.persistence.model.UserType;
import org.springframework.data.annotation.Id;

public class UserDto {

    private String id;
    private String name;
    private String document;
    private String email;
    private UserType type;
    private double balance;

    public UserDto(String id, String name, String document, String email, UserType type,double balance) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
        this.type = type;
        this.balance = balance;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.document = user.getDocument();
        this.email = user.getEmail();
        this.type = user.getType();
        this.balance = user.getBalance();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
