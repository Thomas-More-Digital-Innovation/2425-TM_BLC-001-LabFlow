package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.service.StaalService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void create(@RequestBody Staal staal) {
        staalService.create(staal);
    }

    // read
    @GetMapping("/stalen")
    public List<Staal> read() {
        return staalService.read();
    }

    // update
    @PutMapping("/updatestaal/{id}")
    public ResponseEntity<Staal> update(@PathVariable Long id, @RequestBody Staal staal) {
        return staalService.update(id, staal);
    }

    // delete
    @DeleteMapping ("/deletestaal/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return staalService.delete(id);
    }
}
