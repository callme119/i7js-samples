/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 building blocks
 */
package com.itextpdf.highlevel.chapter03;

import com.itextpdf.highlevel.util.CsvTo2DList;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.test.annotations.WrapToTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author iText
 */
@WrapToTest
public class C03E03_JekyllHydeTabsV3 {
    
    public static final String SRC = "src/main/resources/data/jekyll_hyde.csv";
    public static final String DEST = "results/chapter03/jekyll_hyde_tabs3.pdf";
       
    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C03E03_JekyllHydeTabsV3().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        Document document = new Document(pdf, PageSize.A4.rotate());
        
        float[] stops = new float[]{80, 120, 580, 590, 720};
        List<TabStop> tabstops = new ArrayList();
        tabstops.add(new TabStop(stops[0], TabAlignment.CENTER));
        tabstops.add(new TabStop(stops[1], TabAlignment.LEFT));
        tabstops.add(new TabStop(stops[2], TabAlignment.RIGHT));
        tabstops.add(new TabStop(stops[3], TabAlignment.LEFT));
        TabStop anchor = new TabStop(stops[4], TabAlignment.ANCHOR);
        anchor.setTabAnchor(' ');
        tabstops.add(anchor);
        PdfCanvas pdfCanvas = new PdfCanvas(pdf.addNewPage());
        for (int i = 0; i < stops.length; i++) {
            pdfCanvas.moveTo(document.getLeftMargin() + stops[i], 0);
            pdfCanvas.lineTo(document.getLeftMargin() + stops[i], 595);
        }
        pdfCanvas.stroke();
        
        List<List<String>> resultSet = CsvTo2DList.convert(SRC, "|");
        for (List<String> record : resultSet) {
            Paragraph p = new Paragraph();
            p.addTabStops(tabstops);
            p.add(record.get(0).trim()).add(new Tab())
                .add(record.get(1).trim()).add(new Tab())
                .add(record.get(2).trim()).add(new Tab())
                .add(record.get(3).trim()).add(new Tab())
                .add(record.get(4).trim()).add(new Tab())
                .add(record.get(5).trim() + " \'");
            document.add(p);
        }
        document.close();
    }
}
