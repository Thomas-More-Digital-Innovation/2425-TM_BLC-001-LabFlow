package com.thomasmore.blc.labflow.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.thomasmore.blc.labflow.entity.Staal;
import com.thomasmore.blc.labflow.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGeneratorService {
    @Autowired
    private StaalService staalService;

    public byte[] generatePdf(List<Test> staalList) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Adding content to PDF


        return new byte[0];
    }
}
