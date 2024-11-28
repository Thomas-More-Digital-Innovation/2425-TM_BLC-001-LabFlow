package com.thomasmore.blc.labflow.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.thomasmore.blc.labflow.entity.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    public byte[] generateLabelPdf(Staal staal) throws DocumentException {

        // Create a small page size for labels
        Document document = new Document(new Rectangle(210, 140)); // A7 size
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();

        // Extract details from `staal`
        Long staalCode = staal.getStaalCode();
        String voornaam = staal.getPatientVoornaam();
        String achternaam = staal.getPatientAchternaam();
        LocalDate geboortedatum = staal.getPatientGeboorteDatum();
        char geslacht = staal.getPatientGeslacht();

        // Filter categories
        Set<Testcategorie> testcategorieSet = staal.getRegisteredTests().stream()
                .map(StaalTest::getTest)
                .map(Test::getTestcategorie)
                .filter(testcategorie -> testcategorie.getId() != 7)
                .collect(Collectors.toSet());

        // Format the birthdate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = geboortedatum.format(formatter);

        // Set fonts
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Get the canvas for custom drawing
        PdfContentByte canvas = writer.getDirectContent();

        // Draw the label border
        Rectangle border = new Rectangle(10, 10, 200, 130); // Define rectangle dimensions
        border.setBorder(Rectangle.BOX);
        border.setBorderWidth(1);
        canvas.rectangle(border);
        canvas.stroke();

        // Add patient name (Bold)
        Paragraph nameParagraph = new Paragraph(voornaam + " " + achternaam, boldFont);
        nameParagraph.setAlignment(Element.ALIGN_LEFT);
        document.add(nameParagraph);

        // Add birthdate and gender
        document.add(new Paragraph("Geboorte: " + formattedDate, regularFont));
        document.add(new Paragraph("Geslacht: " + (geslacht == 'M' ? "Man" : "Vrouw"), regularFont));

        // Add some spacing
        document.add(Chunk.NEWLINE);

        // Add the `staalCode` inside the black rectangle at the bottom
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                new Phrase(String.valueOf(staalCode), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)),
                105, 25, 0); // Center the text inside the black rectangle

        // Iterate through each `Testcategorie` and create a page for each label
        for (Testcategorie testcategorie : testcategorieSet) {
            document.newPage(); // Add a new page for every label

            // Draw the label border
            Rectangle newBorder = new Rectangle(10, 10, 200, 130); // Create a new rectangle for each page
            newBorder.setBorder(Rectangle.BOX);
            newBorder.setBorderWidth(1);
            canvas.rectangle(newBorder);
            canvas.stroke();

            // Add patient name (Bold)
            nameParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(nameParagraph);

            // Add birthdate and gender
            document.add(new Paragraph("Geboorte: " + formattedDate, regularFont));
            document.add(new Paragraph("Geslacht: " + (geslacht == 'M' ? "Man" : "Vrouw"), regularFont));

            // Add some spacing
            document.add(Chunk.NEWLINE);

            // Add the `staalCode` inside the black rectangle at the bottom
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    new Phrase(String.valueOf(staalCode), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)),
                    105, 25, 0); // Center the text inside the black rectangle

            // Add vertical EDTA text on the right side
            ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT,
                    new Phrase(testcategorie.getNaam(), boldFont), 185, 60, 270); // Rotated 90 degrees
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
        Font noteFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
        Font headerDataFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

        // Patient info
        List<StaalTest> registeredTests = staal.getRegisteredTests();
        Long staalCode = staal.getStaalCode();
        String voornaam = staal.getPatientVoornaam();
        String achternaam = staal.getPatientAchternaam();
        LocalDate geboortedatum = staal.getPatientGeboorteDatum();
        char geslacht = staal.getPatientGeslacht();
        String laborant = staal.getLaborantNaam();
        String rNummer = staal.getLaborantRnummer();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = geboortedatum.format(formatter);
        // huidige datum
        String formattedCurrentDate = LocalDate.now().format(formatter);


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
        rightCell.addElement(new Paragraph("Datum: " + formattedCurrentDate, bodyFont));
        rightCell.addElement(new Paragraph("Laborant: " + laborant, bodyFont));
        rightCell.addElement(new Paragraph("R-nummer: " + rNummer, bodyFont));// Current date
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
                .filter(test -> test.getTestcategorie().getId() != 7)
                .toList();

        // Get 'notitie' test
        Test notitie = registeredTests.stream()
                .map(StaalTest::getTest)
                .filter(test -> test.getTestcategorie().getId() == 7)
                .findFirst()
                .orElse(null);

        // Loop through test categories and add information
        PdfPTable dataTable = new PdfPTable(6);
        dataTable.setWidthPercentage(100);
        dataTable.setWidths(new int[]{1, 3, 1, 1, 3, 2}); // Adjust column widths

        // Add 'notitie' test
        if (notitie != null) {
            // header columns - removing borders
            PdfPCell testCodeHeader = new PdfPCell(new Phrase(notitie.getTestCode(), bodyFont));
            testCodeHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(testCodeHeader);

            PdfPCell nameHeader = new PdfPCell(new Phrase(notitie.getNaam(), bodyFont));
            nameHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameHeader);

            String result = notitie.getStalen().stream()
                    .filter(staalTest -> staalTest.getStaal().getStaalCode() == staalCode)
                    .map(StaalTest::getResult)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("Geen notitie");

            if (result.equals("Geen notitie")) {
                PdfPCell resultHeader = new PdfPCell(new Phrase("Geen notitie", bodyFont));
                resultHeader.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(resultHeader);
            } else {
                PdfPCell resultHeader = new PdfPCell(new Phrase(result, bodyFont));
                resultHeader.setBorder(Rectangle.NO_BORDER);
                dataTable.addCell(resultHeader);
            }

            PdfPCell unitHeader = new PdfPCell(new Phrase(""));
            unitHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(unitHeader);

            PdfPCell referenceHeader = new PdfPCell(new Phrase("", bodyFont));
            referenceHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(referenceHeader);

            PdfPCell categoryHeader = new PdfPCell(new Phrase("", bodyFont));
            categoryHeader.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(categoryHeader);

            String note = notitie.getStalen().stream()
                    .filter(staalTest -> staalTest.getStaal().getStaalCode() == staalCode)
                    .map(StaalTest::getNote)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

            if (note != null) {
                PdfPCell noteCell = new PdfPCell(new Phrase("Nota: " + note, noteFont));
                noteCell.setPaddingLeft(20);
                noteCell.setPaddingBottom(5);
                noteCell.setBackgroundColor(new BaseColor(239, 239, 239));
                noteCell.setBorder(Rectangle.NO_BORDER);
                noteCell.setColspan(6);
                dataTable.addCell(noteCell);
            }
        }

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

            // get the note for the test
            String note = test.getStalen().stream()
                    .filter(staalTest -> staalTest.getStaal().getStaalCode() == staalCode)
                    .map(StaalTest::getNote)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("No note");

            System.out.println("Note: " + note);

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

            if (!note.equals("No note")) {
                PdfPCell noteCell = new PdfPCell(new Phrase("Nota: " + note, noteFont));
                noteCell.setPaddingLeft(20);
                noteCell.setPaddingBottom(5);
                noteCell.setBackgroundColor(new BaseColor(239, 239, 239));
                noteCell.setBorder(Rectangle.NO_BORDER);
                noteCell.setColspan(6);
                dataTable.addCell(noteCell);
            }
        }

        document.add(dataTable);



        document.close();
        return out.toByteArray();
    }
}
