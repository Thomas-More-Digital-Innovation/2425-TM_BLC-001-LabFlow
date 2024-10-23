package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")  // allow origin van front-end
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "getusers")
    public List<User> readUsers(){
        return userService.readUsers();
    }

    // aanmaken van een user
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return userService.verify(user);
    }
}