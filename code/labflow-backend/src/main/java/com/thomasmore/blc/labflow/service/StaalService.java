package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.StaalTest;
import com.thomasmore.blc.labflow.entity.Test;
import com.thomasmore.blc.labflow.repository.StaalRepository;
import com.thomasmore.blc.labflow.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class StaalService {
    @Autowired
    private StaalRepository staalRepository;

    @Autowired
    private TestRepository testRepository;

    // Create
    public void createStaal(Staal staal) {
        // loopen door elke test
        for (StaalTest registeredTest : staal.getRegisteredTests()) {
            // testobject ophalen en koppelen met staal
            Test test = testRepository.findById(registeredTest.getTest().getId());
            registeredTest.setTest(test);
        }
        staalRepository.save(staal);
    }

    // Read all
    public List<Staal> read() {
        return staalRepository.findAllByOrderByStaalCodeDesc();
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

    // increment van de grootste testcode voor aanmaken nieuwe test
    public String newStaalCode() {

        String grootsteStaalCode = staalRepository.findLargestStaalCode();
        String huidigJaar = String.valueOf(Year.now().getValue());
        String nieuweStaalCode;

        if (grootsteStaalCode != null && grootsteStaalCode.startsWith(huidigJaar)) {
            // verwijderen van eerste 4 karakters (het jaartal) en increment met 1 "YYYY" / "XXXXXX"+1
            int increment = Integer.parseInt(grootsteStaalCode.substring(4)) + 1;
            // https://www.w3schools.com/java/ref_string_format.asp
            // zorgt ervoor dat het incremented deel altijd 6 getallen groot is
            nieuweStaalCode = huidigJaar + String.format("%06d", increment);
        } else {
            // In het geval van een nieuw jaar, starten we vanaf 1
            nieuweStaalCode = huidigJaar + "000001";
        }
        return nieuweStaalCode;
    }
}
