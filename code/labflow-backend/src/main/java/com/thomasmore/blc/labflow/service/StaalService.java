package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.repository.StaalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaalService {
    private StaalRepository staalRepository;

    // create
    public void createStaal(Staal staal) {
        staalRepository.save(staal);
    }

    // read
    public List<Staal> getStalen() {
        return staalRepository.findAll();
    }

    // update
    public ResponseEntity<Staal> updateStaal(Long id, Staal staal) {
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
        return new ResponseEntity<>(updatedStaal, HttpStatus.NOT_FOUND);
    }

    // delete
}
