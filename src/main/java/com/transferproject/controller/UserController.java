package com.transferproject.controller;

import com.transferproject.persistence.model.ChangePassword;
import com.transferproject.persistence.model.Success;
import com.transferproject.persistence.model.dto.NewUserDto;
import com.transferproject.persistence.model.dto.UserDto;
import com.transferproject.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public UserDto findUser(@PathVariable String id) {
        return userService.convertFoundUser(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody NewUserDto newUser) {
        return userService.createUser(newUser);
    }

    @PostMapping(value = "change-password")
    public Success changeUserPassword(@RequestBody ChangePassword changePassword) {
        return userService.changePassword(changePassword);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return true;
    }


}
