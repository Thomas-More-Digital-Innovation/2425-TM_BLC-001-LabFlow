package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // wordt gebruikt voor onder andere login
    User findByEmail(String email);

    public User findById(Long id);

    boolean existsByVoorNaamAndAchterNaam(String voorNaam, String achterNaam);

    User getUserById(long l);
}