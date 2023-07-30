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

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transferToUser(@RequestBody Deposit deposit) {
        String response = userService.transferToUser(deposit);
        return convertToResponse(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, String>> depositToUser(@RequestBody DepositDto deposit) {
        String response = userService.depositIntoAccount(deposit);
        return convertToResponse(response);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return true;
    }

    private ResponseEntity<Map<String, String>> convertToResponse(String message) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String, String> response =  new HashMap<>() {{
            put("message", message);
        }};
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }
}
