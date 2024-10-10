package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.entity.UserPrincipal;
import com.thomasmore.blc.labflow.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// dit zorgt ervoor dat
@Service
public class MyUserDetailsService implements UserDetailsService {

    // repo dat met onze users kan interageren
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = repo.findByEmail(email);

        if(user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
