package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Testcategorie;
import com.thomasmore.blc.labflow.repository.TestCategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestCategorieService {
    @Autowired
    private TestCategorieRepository testCategorieRepository;

    // Create
    public void createTestcategorie(Testcategorie testcategorie) {
        testCategorieRepository.save(testcategorie);
    }

    // Read
    // all
    public List<Testcategorie> allTestcategorie() {
        return testCategorieRepository.findAll();
    }
    // search
    public List<Testcategorie> searchTestCategorie(String partName) {
        return testCategorieRepository.findAllByNaamIsStartingWith(partName);
    }

    // Update
    public ResponseEntity<Testcategorie> updateTestCategorie(Long id, Testcategorie updateTestCategorie) {
        Testcategorie testcategorie = testCategorieRepository.findById(id);
        if (testcategorie != null) {
            testcategorie.setNaam(updateTestCategorie.getNaam());
            testcategorie.setKleur(updateTestCategorie.getKleur());
            testCategorieRepository.save(testcategorie);
            return new ResponseEntity<>(testcategorie, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete
    public ResponseEntity<Integer> deleteTestCategorie(Long id) {
        Testcategorie deleteTestcategorie = testCategorieRepository.findById(id);
        if (deleteTestcategorie != null) {
            testCategorieRepository.delete(deleteTestcategorie);
            return new ResponseEntity<>(testCategorieRepository.findAll().size(), HttpStatus.OK);
        }
        return new ResponseEntity<>(testCategorieRepository.findAll().size(), HttpStatus.NOT_FOUND);
    }
}
