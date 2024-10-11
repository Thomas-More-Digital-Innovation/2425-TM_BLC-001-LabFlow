package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.repository.UserRepository;
// transactional zorgt ervoor dat een methode met meerdere database interacties volgens het ACID principe werkt
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // BcryptEncoder heeft 1 parameter 'strength'
    // hoe hoger het getal, hoe meer het wachtwoord wordt gehasht, maar hoe meer compute nodig is
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public List<User> readUsers(){
        return userRepository.findAll();
    }

    public User register(User user){
        // hashen van het wachtwoord
        user.setWachtwoord(encoder.encode(user.getWachtwoord()));
        return userRepository.save(user);
    }
}
