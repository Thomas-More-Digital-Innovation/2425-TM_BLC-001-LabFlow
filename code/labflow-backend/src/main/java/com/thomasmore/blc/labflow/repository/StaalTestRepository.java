package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.StaalTest;
import com.thomasmore.blc.labflow.entity.StaalTestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;

@Repository
public interface StaalTestRepository extends JpaRepository<StaalTest, Integer> {

    // I want to get a 'staalTest' based on the staalid and testid
    StaalTest findByStaal_IdAndTest_Id(Long staalId, Long testId);
}
