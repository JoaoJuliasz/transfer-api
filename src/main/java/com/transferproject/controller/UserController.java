package com.transferproject.controller;

import com.transferproject.persistence.model.Deposit;
import com.transferproject.persistence.model.dto.DepositDto;
import com.transferproject.persistence.model.dto.NewUserDto;
import com.transferproject.persistence.model.dto.UserDto;
import com.transferproject.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return true;
    }


}
