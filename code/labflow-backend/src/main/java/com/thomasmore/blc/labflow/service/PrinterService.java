package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.repository.StaalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {
    @Autowired
    private StaalRepository staalRepository;

    public ResponseEntity<String> printLabel(String staalId, int amountOfCopies) {
        try {
            if (amountOfCopies < 0) {
                return ResponseEntity.badRequest().body("Amount of copies must be greater than 0");
            }

            // generate ZPL code
            String zplCode = "^XA\n" +
                    "^PW450\n" +
                    "^LL250\n" +
                    "^FO10,10^GB430,230,3^FS\n" +
                    "^FO20,20^A0N,30,30^FD" + "César" + "^FS\n" +
                    "^FO200,20^A0N,30,30^FD" + "Van Leuffelen" + "^FS\n" +
                    "^FO20,60^A0N,25,25^FD" + "Geboorte: 29 juli 2004" + "^FS\n" +
                    "^FO20,100^A0N,25,25^FD" + "Geslacht: Man" + "^FS\n" +
                    "^FO170,200^A0N,25,25^FD" + "2024000001" + "^FS\n" +
                    "^FO390,60^A0N,30,30^FR^FWR^FD" + "Heparine" + "^FS\n" +
                    "^FO360,100^A0N,20,20^FWR^FD" + "groen" + "^FS\n" +
                    "^XZ";

            return ResponseEntity.ok(zplCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error while printing label");
        }
    }

    /*private String generateZplCode(int labelCount) {
        // base label ZPL code
        String standardLabel = "^XA\n" +
                "^PW450\n" +
                "^LL250\n" +
                "^FO10,10^GB430,230,3^FS\n" +
                "^FO20,20^A0N,30,30^FD" + "César" + "^FS\n" +
                "^FO200,20^A0N,30,30^FD" + "Van Leuffelen" + "^FS\n" +
                "^FO20,60^A0N,25,25^FD" + "Geboorte: 29 juli 2004" + "^FS\n" +
                "^FO20,100^A0N,25,25^FD" + "Geslacht: Man" + "^FS\n" +
                "^FO170,200^A0N,25,25^FD" + "2024000001" + "^FS\n" +
                "^FO390,60^A0N,30,30^FR^FWR^FD" + "Heparine" + "^FS\n" +
                "^FO360,100^A0N,20,20^FWR^FD" + "groen" + "^FS\n" +
                "^XZ";

        // generate ZPL per category label

        // create ZPL code total

        // return all ZPL codes
    }*/
}
