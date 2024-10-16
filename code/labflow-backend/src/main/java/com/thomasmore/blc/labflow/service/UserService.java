package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.repository.UserRepository;
// transactional zorgt ervoor dat een methode met meerdere database interacties volgens het ACID principe werkt
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

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

    public ResponseEntity<?> verify(User user) {
        try {
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getWachtwoord()));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user);
                // Stop token in een JSON format
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Map.of("token", token));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
}
