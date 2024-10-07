package com.thomasmore.blc.labflow.config;

import com.thomasmore.blc.labflow.entity.Rol;
import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        userRepository.save(new User("123", "nathanneve@test.be", "Nathan", "Neve", );
    }
}