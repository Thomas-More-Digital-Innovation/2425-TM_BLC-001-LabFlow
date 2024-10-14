package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.Testcategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaalRepository extends JpaRepository<Staal, Integer> {
    public Staal findById(Long id);
    public void delete(Staal staal);
}
