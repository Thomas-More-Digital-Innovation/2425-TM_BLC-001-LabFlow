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

    // create
    public void create(Staal staal) {
        staalRepository.save(staal);
    }

    // read
    public List<Staal> read() {
        return staalRepository.findAll();
    }

    // update
    public ResponseEntity<Staal> update(Long id, Staal staal) {
        Staal updatedStaal = staalRepository.findById(id);
        if (updatedStaal != null) {
            updatedStaal.setStaalCode(staal.getStaalCode());
            updatedStaal.setLaborantNaam(staal.getLaborantNaam());
            updatedStaal.setUser(staal.getUser());
            updatedStaal.setTests(staal.getTests());
            updatedStaal.setLaborantRnummer(staal.getLaborantRnummer());
            updatedStaal.setPatientAchternaam(staal.getPatientAchternaam());
            updatedStaal.setPatientVoornaam(staal.getPatientVoornaam());
            updatedStaal.setPatientGeboorteDatum(staal.getPatientGeboorteDatum());
            updatedStaal.setPatientGeslacht(staal.getPatientGeslacht());
            return new ResponseEntity<>(updatedStaal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete
    public ResponseEntity<Integer> delete(Long id) {
        Staal deleteStaal = staalRepository.findById(id);
        if (deleteStaal != null) {
            staalRepository.delete(deleteStaal);
            return new ResponseEntity<>(staalRepository.findAll().size(), HttpStatus.OK);
        }
        return new ResponseEntity<>(staalRepository.findAll().size(), HttpStatus.NOT_FOUND);
    }
}
