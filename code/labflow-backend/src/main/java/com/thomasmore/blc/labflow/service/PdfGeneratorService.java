package com.thomasmore.blc.labflow.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.Test;
import com.thomasmore.blc.labflow.entity.Testcategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    public byte[] generateLabelPdf(Staal staal) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // staal info
        List<Test> tests = staal.getTests();
        int staalCode = staal.getStaalCode();
        String voornaam = staal.getPatientVoornaam();
        String achternaam = staal.getPatientAchternaam();
        Date geboortedatum = staal.getPatientGeboorteDatum();
        char geslacht = staal.getPatientGeslacht();

        // date formatter
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(geboortedatum);

        // filter categories
        Set<Testcategorie> testcategorieSet = staal.getTests().stream().map(Test::getTestcategorie).collect(Collectors.toSet());

        // start label
        document.add(new Paragraph("Testcode: " + staalCode));
        document.add(new Paragraph(voornaam + " " + achternaam));
        document.add(new Paragraph("Geboorte: " + formattedDate));
        document.add(new Paragraph("Geslacht: " + geslacht));
        document.add(new Paragraph("--------------------------------"));
        document.add(new Paragraph("\n"));

        // Adding content to PDF
        for (Testcategorie testcategorie : testcategorieSet) {
            document.add(new Paragraph("Testcode: " + staalCode));
            document.add(new Paragraph(voornaam + " " + achternaam));
            document.add(new Paragraph("Geboorte: " + formattedDate));
            document.add(new Paragraph("Geslacht: " + geslacht));
            document.add(new Paragraph(testcategorie.getKleur() + " " + testcategorie.getNaam()));
            document.add(new Paragraph("--------------------------------"));
            document.add(new Paragraph("\n"));
        }

        document.close();
        return out.toByteArray();
    }
}
