package com.example.userservice.UserService.Controller;

import com.example.userservice.UserService.Model.User;
import com.example.userservice.UserService.UserService.UserService;
import com.example.userservice.UserService.Wrapper.UserWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public User createUser(@Valid @RequestBody UserWrapper userWrapper){
        return userService.createUser(userWrapper.to());
    }
    @GetMapping("/get-user")
    public User getUserById(@RequestParam("id")int id){
        return userService.getUserById(id);
    }
}
