package com.transferproject.controller;

import com.transferproject.persistence.model.Deposit;
import com.transferproject.persistence.model.dto.DepositDto;
import com.transferproject.service.TransactionService;
import com.transferproject.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> transferToUser(@RequestBody Deposit deposit) {
        String response = transactionService.transferToUser(deposit);
        return convertToResponse(response);
    }

    @PostMapping("/account")
    public ResponseEntity<Map<String, String>> depositToUser(@RequestBody DepositDto deposit) {
        String response = transactionService.depositIntoAccount(deposit);
        return convertToResponse(response);
    }

    private ResponseEntity<Map<String, String>> convertToResponse(String message) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String, String> response =  new HashMap<>() {{
            put("message", message);
        }};
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}
