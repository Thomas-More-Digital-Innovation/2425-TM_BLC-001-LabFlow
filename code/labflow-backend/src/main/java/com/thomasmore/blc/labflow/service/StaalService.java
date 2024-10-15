package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.repository.StaalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaalService {
    @Autowired
    private StaalRepository staalRepository;

    // Create
    public void create(Staal staal) {
        staalRepository.save(staal);
    }

    // Read all
    public List<Staal> read() {
        return staalRepository.findAll();
    }

    // Update
    public ResponseEntity<Staal> update(Long id, Staal staal) {
        Staal existingStaal = staalRepository.findById(id);
        if (existingStaal != null) {

            // Update the fields
            existingStaal.setStaalCode(staal.getStaalCode());
            existingStaal.setLaborantNaam(staal.getLaborantNaam());
            existingStaal.setUser(staal.getUser());
            existingStaal.setRegisteredTests(staal.getRegisteredTests()); // Fix to match correct field
            existingStaal.setLaborantRnummer(staal.getLaborantRnummer());
            existingStaal.setPatientAchternaam(staal.getPatientAchternaam());
            existingStaal.setPatientVoornaam(staal.getPatientVoornaam());
            existingStaal.setPatientGeboorteDatum(staal.getPatientGeboorteDatum());
            existingStaal.setPatientGeslacht(staal.getPatientGeslacht());

            // Save the updated entity
            Staal updatedStaal = staalRepository.save(existingStaal);
            return new ResponseEntity<>(updatedStaal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete
    public ResponseEntity<Integer> delete(Long id) {
        Staal existingStaal = staalRepository.findById(id);
        if (existingStaal != null) {
            staalRepository.delete(existingStaal);
            return new ResponseEntity<>(staalRepository.findAll().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(staalRepository.findAll().size(), HttpStatus.NOT_FOUND);
        }
    }

    // Get by id
    public Staal readById(Long id) {
        return staalRepository.findById(id);
    }
}
