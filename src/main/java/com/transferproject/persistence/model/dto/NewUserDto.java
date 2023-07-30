package com.transferproject.persistence.model.dto;

import com.transferproject.persistence.model.UserType;
import org.springframework.data.annotation.Id;

public class NewUserDto {

    @Id
    private String id;
    private String name;
    private String document;
    private String email;
    private String password;
    private UserType type;

    public NewUserDto(String id, String name, String document, String email, String password, UserType type) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
        this.password = password;
        this.type = type;
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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
