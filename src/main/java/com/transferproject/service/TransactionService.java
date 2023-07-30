package com.transferproject.service;

import com.transferproject.persistence.model.Deposit;
import com.transferproject.persistence.model.User;
import com.transferproject.persistence.model.dto.DepositDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {

    private final UserService userService;

    public TransactionService(UserService userService) {
        this.userService = userService;
    }

    public String transferToUser(Deposit deposit) {
        User sender = userService.findUser(deposit.getSender());
        User receiver = userService.findUser(deposit.getReceiver());

        userService.checkUser(sender, "Sender user does not exist");
        userService.checkUser(receiver, "Receiver user does not exist");
        userService.checkBalance(sender, deposit.getValue());

        sender.setBalance(sender.getBalance() - deposit.getValue());
        receiver.setBalance(receiver.getBalance() + deposit.getValue());

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

}
