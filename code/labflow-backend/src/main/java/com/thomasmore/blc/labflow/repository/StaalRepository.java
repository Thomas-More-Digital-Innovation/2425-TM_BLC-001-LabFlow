package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.Testcategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaalRepository extends JpaRepository<Staal, Integer> {
    public Staal findById(Long id);

    public void delete(Staal staal);

    public List<Staal> findAllByOrderByStaalCodeDesc();

    // verkrijgen grootste staalcode voor het aanmaken van een nieuwe staal
    @Query("SELECT MAX(t.staalCode) FROM Staal t")
    String findLargestStaalCode();

    // verkrijg staal op basis van staalcode
    public Staal findByStaalCode(Long staalCode);
}
