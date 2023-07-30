package com.transferproject.controller;

import com.transferproject.persistence.model.User;
import com.transferproject.persistence.model.dto.NewUserDto;
import com.transferproject.persistence.model.dto.UserDto;
import com.transferproject.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody NewUserDto newUser) {
        return userService.createUser(newUser);
    }

}
