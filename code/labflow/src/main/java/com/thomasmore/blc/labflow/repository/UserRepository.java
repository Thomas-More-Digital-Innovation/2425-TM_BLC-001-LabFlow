package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findById(Long id);

    boolean existsByVoorNaamAndAchterNaam(String voorNaam, String achterNaam);

    // methode om maximum id te vinden voor nieuwe pk aan te maken
    @Query("select max(u.id) from User u")
    public Integer findMaxId();
}