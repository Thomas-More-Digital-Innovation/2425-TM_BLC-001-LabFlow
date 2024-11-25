package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/printer")
public class PrinterController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/labels/{staalId}/{amountOfCopies}")
    public ResponseEntity<String> generateLabel(@PathVariable String staalId, @PathVariable int amountOfCopies) {
        try {
            return printerService.printLabel(staalId, amountOfCopies);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while printing label");
        }
    }
}
