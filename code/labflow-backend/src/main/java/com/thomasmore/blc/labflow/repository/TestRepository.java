package com.thomasmore.blc.labflow.repository;

import com.thomasmore.blc.labflow.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    public Test findById(Long id);

    public void delete(Test test);

    public Test findByTestCode(String testCode);

    public List<Test> findAllByOrderByTestCodeAsc();
}
