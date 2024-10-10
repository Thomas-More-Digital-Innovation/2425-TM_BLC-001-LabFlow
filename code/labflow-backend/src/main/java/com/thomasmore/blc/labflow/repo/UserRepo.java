package com.thomasmore.blc.labflow.repo;

import com.thomasmore.blc.labflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
