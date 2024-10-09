package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.repository.UserRepository;
// transactional zorgt ervoor dat een methode met meerdere database interacties volgens het ACID principe werkt
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> readUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public String createUser(User user){
        try {
            if (!userRepository.existsByVoorNaamAndAchterNaam(user.getVoorNaam(), user.getAchterNaam())){
                userRepository.save(user);
                return "User created successfully.";
            }else {
                return "User already exists in the database.";
            }
        }catch (Exception e){
            throw e;
        }
    }
}
