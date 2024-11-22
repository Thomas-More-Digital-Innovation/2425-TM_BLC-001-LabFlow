package com.thomasmore.blc.labflow.controller;

import com.thomasmore.blc.labflow.entity.PrintRequest;
import com.thomasmore.blc.labflow.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/printer")
public class PrinterController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/label")
    public ResponseEntity<String> generateLabel(@RequestBody PrintRequest request) {
        try {
            return printerService.printLabel(request);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while printing label");
        }
    }
}
