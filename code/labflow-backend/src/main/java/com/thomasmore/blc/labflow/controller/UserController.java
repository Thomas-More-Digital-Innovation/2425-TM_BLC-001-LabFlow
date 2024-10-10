package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getusers", method = RequestMethod.GET)
    public List<User> readUsers(){
        return userService.readUsers();
    }

    @RequestMapping(value = "createuser", method = RequestMethod.POST)
    public String createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}