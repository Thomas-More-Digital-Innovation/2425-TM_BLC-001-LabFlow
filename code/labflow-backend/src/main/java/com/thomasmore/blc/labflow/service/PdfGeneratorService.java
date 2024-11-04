package com.thomasmore.blc.labflow.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.thomasmore.blc.labflow.entity.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    public int placeholder() {
        return 0;
    }

    public byte[] generateLabelPdf(Staal staal) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // staal info
        Long staalCode = staal.getStaalCode();
        String voornaam = staal.getPatientVoornaam(); 
        String achternaam = staal.getPatientAchternaam();
        Date geboortedatum = staal.getPatientGeboorteDatum();
        char geslacht = staal.getPatientGeslacht();

        // date formatter
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(geboortedatum);

        // filter categories
        Set<Testcategorie> testcategorieSet = staal.getRegisteredTests().stream().map(StaalTest::getTest)
                .map(Test::getTestcategorie)
                .collect(Collectors.toSet());

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
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10);
        Font headerDataFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

        // Logo
        // Image logo = Image.getInstance("/assets/labflowLogo.png");
        // logo.scaleToFit(50, 50);
        // document.add(logo); // Uncomment this if you have a logo image

        // Patient info
        List<StaalTest> registeredTests = staal.getRegisteredTests();
        Long staalCode = staal.getStaalCode();
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

        // setup data layout
        PdfPTable headerDataTable = new PdfPTable(6);
        headerDataTable.setWidthPercentage(100);
        headerDataTable.setWidths(new int[]{1, 3, 1, 1, 3, 2}); // Adjust column widths

        // header columns
        headerDataTable.addCell(new PdfPCell(new Phrase("Code", headerDataFont)));
        headerDataTable.addCell(new PdfPCell(new Phrase("Naam", headerDataFont)));
        headerDataTable.addCell(new PdfPCell(new Phrase("Resultaat", headerDataFont)));
        headerDataTable.addCell(new PdfPCell(new Phrase("Eenheid", headerDataFont)));
        headerDataTable.addCell(new PdfPCell(new Phrase("referentie", headerDataFont)));
        headerDataTable.addCell(new PdfPCell(new Phrase("Categorie", headerDataFont)));

        document.add(headerDataTable);
        document.add(new Paragraph("\n"));// Adding space after the header

        // Get all tests and order by test categories
        List<Test> testList = registeredTests.stream()
                .map(StaalTest::getTest)
                .sorted(Comparator.comparing(test -> test.getTestcategorie().getNaam()))
                .toList();

        // Loop through test categories and add information
        PdfPTable dataTable = new PdfPTable(6);
        dataTable.setWidthPercentage(100);
        dataTable.setWidths(new int[]{1, 3, 1, 1, 3, 2}); // Adjust column widths

        for (Test test : testList) {
            // header columns - removing borders
            PdfPCell testCodeHeader = new PdfPCell(new Phrase(test.getTestCode(), bodyFont));
            testCodeHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(testCodeHeader);

            PdfPCell nameHeader = new PdfPCell(new Phrase(test.getNaam(), bodyFont));
            nameHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameHeader);

            String result = test.getStalen().stream()
                    .filter(staalTest -> staalTest.getStaal().getStaalCode() == staalCode)
                    .map(StaalTest::getResult)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("No result");

            if (result.equals("No result")) {
                PdfPCell resultHeader = new PdfPCell(new Phrase("no result", bodyFont));
                resultHeader.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(resultHeader);
            } else {
                PdfPCell resultHeader = new PdfPCell(new Phrase(result, bodyFont));
                resultHeader.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(resultHeader);
            }

            PdfPCell unitHeader = new PdfPCell(new Phrase(test.getEenheid().getAfkorting(), bodyFont));
            unitHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(unitHeader);

            Set<Referentiewaarde> referentiewaardes = test.getReferentiewaardes();
            if (referentiewaardes.size() > 0) {
                String referentieWaarden = referentiewaardes.stream()
                        .map(Referentiewaarde::getWaarde)
                        .collect(Collectors.joining(" / "));
                PdfPCell referenceHeader = new PdfPCell(new Phrase(referentieWaarden, bodyFont));
                referenceHeader.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(referenceHeader);
            } else {
                PdfPCell referenceHeader = new PdfPCell(new Phrase("No reference value", bodyFont));
                referenceHeader.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(referenceHeader);
            }

            PdfPCell categoryHeader = new PdfPCell(new Phrase(test.getTestcategorie().getNaam(), bodyFont));
            categoryHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(categoryHeader);
        }

        document.add(dataTable);



        document.close();
        return out.toByteArray();
    }
}
