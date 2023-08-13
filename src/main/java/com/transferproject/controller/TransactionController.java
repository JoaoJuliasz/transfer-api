package com.transferproject.controller;

import com.transferproject.persistence.model.Deposit;
import com.transferproject.persistence.model.Success;
import com.transferproject.persistence.model.dto.DepositDto;
import com.transferproject.service.TransactionService;
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
    public ResponseEntity<Success> transferToUser(@RequestBody Deposit deposit) {
        String response = transactionService.transferToUser(deposit);
        return convertToResponse(response);
    }

    @PostMapping("/account")
    public ResponseEntity<Success> depositToUser(@RequestBody DepositDto deposit) {
        String response = transactionService.depositIntoAccount(deposit);
        return convertToResponse(response);
    }

    private ResponseEntity<Success> convertToResponse(String message) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Success response = new Success(message);
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}
