package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.PrintRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {

    public ResponseEntity<String> printLabel(PrintRequest request) {
        try {
            if (request.getAmountOfCopies() > 0) {
                return ResponseEntity.badRequest().body("Amount of copies must be greater than 0");
            }

            // generate ZPL code
            String zplCode = "Hello world!";

            return ResponseEntity.ok(zplCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while printing label");
        }
    }
}
