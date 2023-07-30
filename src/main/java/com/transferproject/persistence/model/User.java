package com.transferproject.persistence.model;

import com.transferproject.persistence.model.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {

    @Id
    private String id;
    private String name;
    private String document;
    private String email;
    private String password;
    private double balance;

    public User() {}

    public User(String id, String name, String document, String email, String password, double balance) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.name = userDto.getName();
        this.document = userDto.getDocument();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.balance = 0.00;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
