package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.StaalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;

@Repository
public interface StaalTestRepository extends JpaRepository<StaalTest, Integer> {

}
