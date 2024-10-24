package com.thomasmore.blc.labflow.controller;

import com.itextpdf.text.DocumentException;
import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.service.PdfGeneratorService;
import com.thomasmore.blc.labflow.service.StaalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/pdf")
public class PdfGeneratorController {
    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private StaalService staalService;

    @GetMapping("/generatelabel/{id}")
    public ResponseEntity<String> generateLabelPdf(@PathVariable Long id) {

        Staal staal = staalService.readById(id);
        byte[] pdfBytes;

        try {
            // Generate PDF bytes
            pdfBytes = pdfGeneratorService.generateLabelPdf(staal);

            // Define the directory to save the PDF file (e.g., Downloads folder)
            String downloadsDir = System.getProperty("user.home") + "/Documents/school folder/3DI/Labflow/2425-TM_BLC-001-LabFlow/code/labflow-backend/files/";
            String pdfFileName = "label_" + staal.getId() + ".pdf";
            Path pdfFilePath = Paths.get(downloadsDir, pdfFileName);

            // Save the PDF to the file system
            Files.write(pdfFilePath, pdfBytes);

            // Construct the URL where the file can be accessed (adjust host and port as needed)
            String fileUrl = "http://localhost:8080/files/" + pdfFileName;

            // Return the link as a response
            return ResponseEntity.ok(fileUrl);

        } catch (DocumentException | IOException e) {
            // If an error occurs, return a 500 Internal Server Error response
            return ResponseEntity.internalServerError().body("Failed to generate or save PDF.");
        }
    }


    @GetMapping("/generateresults/{id}")
    public ResponseEntity<byte[]> generateResultsPdf(@PathVariable Long id) {

        Staal staal = staalService.readById(id);
        byte[] pdfBytes;

        try {
            pdfBytes = pdfGeneratorService.generateResultsPdf(staal);
        } catch (DocumentException e) {
            return ResponseEntity.internalServerError().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "results_"+ staal.getPatientAchternaam() + "_" + staal.getPatientVoornaam() +".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
