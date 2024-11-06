package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Test;
import com.thomasmore.blc.labflow.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.parser.Parser;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;


    // create
    public void create(Test test) {
        testRepository.save(test);
    }

    // read
    public List<Test> read() {
        return testRepository.findAllSortedByTestCode();
    }

    // update
    public ResponseEntity<Test> update(Long id, Test test) {
        Test updateTest = testRepository.findById(id);
        if (updateTest != null) {
            updateTest.setNaam(test.getNaam());
            updateTest.setTestCode(test.getTestCode());
            updateTest.setEenheid(test.getEenheid());
            updateTest.setTestcategorie(test.getTestcategorie());
            testRepository.save(updateTest);
            return new ResponseEntity<>(updateTest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete
    public ResponseEntity<Integer> delete(Long id) {
        Test deleteTest = testRepository.findById(id);
        if (deleteTest != null) {
            testRepository.delete(deleteTest);
            return new ResponseEntity<>(testRepository.findAll().size(), HttpStatus.OK);
        }
        return new ResponseEntity<>(testRepository.findAll().size(), HttpStatus.NOT_FOUND);
    }
}