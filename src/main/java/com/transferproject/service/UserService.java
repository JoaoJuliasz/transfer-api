package com.transferproject.service;

import com.transferproject.persistence.model.*;
import com.transferproject.persistence.model.dto.DepositDto;
import com.transferproject.persistence.model.dto.NewUserDto;
import com.transferproject.persistence.model.dto.UserDto;
import com.transferproject.persistence.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public UserDto convertFoundUser(String id) {
        User user = findUserById(id);
        return convertToDto(user);
    }

    private User findUserByName(String name) {
        User foundUser = userRepository.findUserByName(name).block();
        if (foundUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with name '" + name + "' not exits");
        }

        return foundUser;
    }

    public User findUserById(String id) {
        User foundUser = userRepository.findById(id).block();

        if (foundUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id '" + id + "' not exits");
        }

        return foundUser;
    }

    public void deleteUser(String id) {
        User user = findUserById(id);
        userRepository.deleteById(user.getId())
                .hasElement()
                .blockOptional();
    }
    
    public Success changePassword(ChangePassword changePassword) {
        User user = findUserByName(changePassword.getUserName());
        validatePassword(user.getPassword(), changePassword.getOldPassword());
        user.setPassword(encryptPassword(changePassword.getNewPassword()));
        saveUser(user);
        return new Success("Password changed!");
    }

    private void validatePassword(String userPassword, String password) {
        if(!passwordEncoder.matches(password, userPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
    }

    public void checkUser(User user, String message) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
    }

    public void checkBalance(User sender, double value) {
        if (sender.getBalance() - value < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }
    }

    public void checkUserType(User sender) {
        if(sender.getType() == UserType.SELLER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sellers can only receive transfers");
        }
    }

    public void saveUser(User user) {
        userRepository.save(user).block();
    }
}
