package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getusers", method = RequestMethod.GET)
    public List<User> readUsers(){
        return userService.readUsers();
    }

    // aanmaken van een user
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);
    }

}