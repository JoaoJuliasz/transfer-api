package com.transferproject.service;

import com.transferproject.persistence.model.User;
import com.transferproject.persistence.model.dto.UserDto;
import com.transferproject.persistence.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

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


    public User createUser(UserDto newUser) {
        emailAlreadyExists(newUser.getEmail());
        if (emailAlreadyExists(newUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already being used");
        }
        if (documentAlreadyExists(newUser.getDocument())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document is already being used");
        }
        User user = new User(newUser);
        return userRepository.save(user).block();
    }

}
