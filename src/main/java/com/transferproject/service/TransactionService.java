package com.transferproject.service;

import com.transferproject.persistence.model.Deposit;
import com.transferproject.persistence.model.User;
import com.transferproject.persistence.model.dto.DepositDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class TransactionService {

    private static final String BASE_URL = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6";

    private final RestTemplate restTemplate;

    private final UserService userService;

    public TransactionService(UserService userService, RestTemplateBuilder builder) {
        this.userService = userService;
        this.restTemplate = builder.build();
    }

    public String transferToUser(Deposit deposit) {
        User sender = userService.findUser(deposit.getSender());
        User receiver = userService.findUser(deposit.getReceiver());

        userService.checkUser(sender, "Sender user does not exist");
        userService.checkUser(receiver, "Receiver user does not exist");
        userService.checkBalance(sender, deposit.getValue());

        sender.setBalance(sender.getBalance() - deposit.getValue());
        receiver.setBalance(receiver.getBalance() + deposit.getValue());
        if(!validateTransaction()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Transaction has not been authorized");
        }
        userService.saveUser(sender);
        userService.saveUser(receiver);
        return "Deposit of $" + deposit.getValue() + " made  from " + sender.getName() + ", to " + receiver.getName();
    }

    public String depositIntoAccount(DepositDto deposit) {
        if (deposit.getValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deposit value must be bigger than 0");
        }
        User receiver = userService.findUser(deposit.getReceiver());
        userService.checkUser(receiver, "User does not exist");
        receiver.setBalance(receiver.getBalance() + deposit.getValue());
        userService.saveUser(receiver);
        return "Value of $" + deposit.getValue() + " was deposited into" + receiver.getName() + " account";

    }

    private boolean validateTransaction() {
        ResponseEntity<Map> response = restTemplate.getForEntity(BASE_URL, Map.class);
        var responseStatus = response.getBody().get("message");
        return responseStatus.equals("Autorizado");
    }

}
