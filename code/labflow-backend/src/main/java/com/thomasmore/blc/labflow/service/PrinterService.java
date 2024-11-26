package com.thomasmore.blc.labflow.service;

import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.StaalTest;
import com.thomasmore.blc.labflow.entity.Test;
import com.thomasmore.blc.labflow.entity.Testcategorie;
import com.thomasmore.blc.labflow.repository.StaalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PrinterService {
    @Autowired
    private StaalRepository staalRepository;

    public ResponseEntity<String> printLabel(Long staalId, int amountOfCopies) {
        try {
            if (amountOfCopies < 0) {
                return ResponseEntity.badRequest().body("Amount of copies must be greater than 0");
            }

            // get staal
            Staal staal = staalRepository.findById(staalId);

            // geboortedatum formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedGeboorte = staal.getPatientGeboorteDatum().format(formatter);

            // get all categories of the tests
            Set<Testcategorie> testcategorieSet = staal.getRegisteredTests().stream().map(StaalTest::getTest)
                    .map(Test::getTestcategorie)
                    .filter(testcategorie -> testcategorie.getId() != 7)
                    .collect(Collectors.toSet());

            // format geslacht
            char geslacht = staal.getPatientGeslacht();
            String formattedGeslacht;
            if (geslacht == 'M') {
                formattedGeslacht = "Man";
            } else {
                formattedGeslacht = "Vrouw";
            }

            String zplCode = "";

            for (int i = 0; i < amountOfCopies; i++) {
                // basic label
                zplCode = "^XA\n" +
                        "^PW450\n" +
                        "^LL250\n" +
                        "^FO10,15^GB430,230,3^FS\n" +
                        "^FO20,25^A0N,30,30^FD" + staal.getPatientVoornaam() + "^FS\n" +
                        "^FO200,25^A0N,30,30^FD" + staal.getPatientAchternaam() + "^FS\n" +
                        "^FO20,65^A0N,25,25^FD" + "Geboorte: " + formattedGeboorte + "^FS\n" +
                        "^FO20,105^A0N,25,25^FD" + "Geslacht: " + formattedGeslacht + "^FS\n" +
                        "^FO170,205^A0N,25,25^FD" + staal.getStaalCode() + "^FS\n" +
                        "^XZ\n";

                // label per category
                for (Testcategorie testcategorie : testcategorieSet) {
                    zplCode += "^XA\n" +
                            "^PW450\n" +
                            "^LL250\n" +
                            "^FO10,15^GB430,230,3^FS\n" +
                            "^FO20,25^A0N,30,30^FD" + staal.getPatientVoornaam() + "^FS\n" +
                            "^FO200,25^A0N,30,30^FD" + staal.getPatientAchternaam() + "^FS\n" +
                            "^FO20,65^A0N,25,25^FD" + "Geboorte: " + staal.getPatientGeboorteDatum() + "^FS\n" +
                            "^FO20,105^A0N,25,25^FD" + "Geslacht: " + staal.getPatientGeslacht() + "^FS\n" +
                            "^FO170,205^A0N,25,25^FD" + staal.getStaalCode() + "^FS\n" +
                            "^FO390,65^A0N,30,30^FR^FWR^FD" + testcategorie.getNaam() + "^FS\n" +
                            "^XZ\n";
                }
            }




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
