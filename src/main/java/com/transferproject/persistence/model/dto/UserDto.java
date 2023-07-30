package com.transferproject.persistence.model.dto;

import org.springframework.data.annotation.Id;

public class UserDto {

    @Id
    private String id;
    private String name;
    private String document;
    private String email;
    private String password;

    public UserDto(String id, String name, String document, String email, String password) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
        this.password = password;
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
}
