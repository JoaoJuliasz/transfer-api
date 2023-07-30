package com.transferproject.service;

import com.transferproject.persistence.model.Deposit;
import com.transferproject.persistence.model.User;
import com.transferproject.persistence.model.dto.NewUserDto;
import com.transferproject.persistence.model.dto.UserDto;
import com.transferproject.persistence.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final IUserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

    }

    private boolean emailAlreadyExists(String email) {
        return userRepository.findUserEmail(email)
                .hasElement()
                .blockOptional()
                .orElse(false);
    }

    private boolean documentAlreadyExists(String document) {
        return userRepository.findUserDocument(document)
                .hasElement()
                .blockOptional()
                .orElse(false);
    }


    public UserDto createUser(NewUserDto newUser) {
        if (emailAlreadyExists(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already being used");
        }
        if (documentAlreadyExists(newUser.getDocument())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document is already being used");
        }
        User user = new User(newUser);
        user.setPassword(encryptPassword(user.getPassword()));
        return convertToDto(userRepository.save(user).block());
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user);
    }

    private User findUser(String id) {
        return userRepository.findById(id).block();
    }

    public void deleteUser(String id) {
        User user = findUser(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '" + id + "' not exits");
        }

        userRepository.deleteById(id)
                .hasElement()
                .blockOptional();
    }

    public String transferToUser(Deposit deposit) {
        User sender = findUser(deposit.getSender());
        User receiver = findUser(deposit.getReceiver());
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender user does not exist");
        }
        if (receiver == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver user does not exist");
        }
        if (sender.getBalance() - deposit.getValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }
        sender.setBalance(sender.getBalance() - deposit.getValue());
        receiver.setBalance(receiver.getBalance() + deposit.getValue());

        userRepository.save(sender).block();
        userRepository.save(receiver).block();
        return "Deposit of $" + deposit.getValue() + " made  from " + sender.getName() + ", to " + receiver.getName();
    }

}
