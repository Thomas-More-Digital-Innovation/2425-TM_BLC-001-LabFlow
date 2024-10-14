package com.thomasmore.blc.labflow.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.Test;
import com.thomasmore.blc.labflow.entity.Testcategorie;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public byte[] generateResultsPdf(Staal staal) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Font settings
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10);
        Font smallFont = new Font(Font.FontFamily.HELVETICA, 8);

        // Logo
        // Image logo = Image.getInstance("/assets/labflowLogo.png");
        // logo.scaleToFit(50, 50);
        // document.add(logo); // Uncomment this if you have a logo image

        // Patient info
        List<Test> tests = staal.getTests();
        int staalCode = staal.getStaalCode();
        String voornaam = staal.getPatientVoornaam();
        String achternaam = staal.getPatientAchternaam();
        Date geboortedatum = staal.getPatientGeboorteDatum();
        char geslacht = staal.getPatientGeslacht();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(geboortedatum);

        // Create table for header section
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new int[]{3, 1});  // Adjust the column widths as needed

        // Left section (Patient details)
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);

        leftCell.addElement(new Paragraph("PATIËNT", headerFont));
        leftCell.addElement(new Paragraph(voornaam + " " + achternaam, bodyFont));
        leftCell.addElement(new Paragraph("Geboorte: " + formattedDate, bodyFont));
        leftCell.addElement(new Paragraph("Geslacht: " + geslacht, bodyFont));
        headerTable.addCell(leftCell);

        // Right section (Test code and date)
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.addElement(new Paragraph("Testcode: " + staalCode, bodyFont));
        rightCell.addElement(new Paragraph("Datum: " + formatter.format(new Date()), bodyFont));  // Current date
        headerTable.addCell(rightCell);

        document.add(headerTable);
        document.add(new Paragraph("\n"));  // Adding space after the header

        // Add a separator line
        document.add(new LineSeparator());

        // Filter categories
        Set<Testcategorie> testcategorieSet = tests.stream()
                .map(Test::getTestcategorie)
                .collect(Collectors.toSet());

        // Loop through test categories and add information
        for (Testcategorie testcategorie : testcategorieSet) {
            document.add(new Paragraph(testcategorie.getNaam()));  // Category name as a header

            // Add a table with some test data (you can adjust it based on your needs)
            PdfPTable testTable = new PdfPTable(2);
            testTable.setWidthPercentage(100);
            testTable.setWidths(new int[]{1, 1});  // Adjust column widths
            testTable.addCell(new PdfPCell(new Phrase("Testcode:", smallFont)));
            testTable.addCell(new PdfPCell(new Phrase(String.valueOf(staalCode), bodyFont)));
            testTable.addCell(new PdfPCell(new Phrase("Naam:", smallFont)));
            testTable.addCell(new PdfPCell(new Phrase(voornaam + " " + achternaam, bodyFont)));

            document.add(testTable);
            document.add(new Paragraph("\n"));  // Space between tests
            document.add(new LineSeparator());  // Separator after each test category
        }

        document.close();
        return out.toByteArray();
    }
}
