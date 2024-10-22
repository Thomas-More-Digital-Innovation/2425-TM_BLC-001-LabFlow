package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.config.UniqueConstraintViolationException;
import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.service.StaalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StaalController {
    @Autowired
    private StaalService staalService;

    // create
    @PostMapping("/createstaal")
    public ResponseEntity<String> createStaal(@RequestBody Staal staal) {
        try {
            staalService.createStaal(staal);
            return ResponseEntity.status(HttpStatus.CREATED).body("Staal created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating Staal");
        }
    }

    // read
    @GetMapping("/stalen")
    public List<Staal> read() {
        return staalService.read();
    }

    // update
    @PutMapping("/updatestaal/{id}")
    public ResponseEntity<Staal> update(@PathVariable Long id, @RequestBody Staal staal) {
        try {
            return staalService.update(id, staal);
        } catch (Exception e) {
            throw new UniqueConstraintViolationException(e.getMessage());
        }
    }

    // delete
    @DeleteMapping ("/deletestaal/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return staalService.delete(id);
    }

    // Krijg de grootste StaalCode + 1 voor een nieuwe test aan te maken
    @GetMapping("/newStaalCode")
    public String newStaalCode() { return staalService.newStaalCode(); }

    // Krijg een staal op basis van id
    @GetMapping("/staal/{id}")
    public Staal getStaal(@PathVariable Long id) {
        return staalService.readById(id);
    }
}
