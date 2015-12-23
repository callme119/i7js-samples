package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.TrueTypeCollection;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_11_02_TTCExample extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter11/Listing_11_02_TTCExample.pdf";
    // TODO Notice that we'va changed windows MS Gothic to IPA Gothic so the results in comparison with itext5 are different
    public static final String FONT = "./src/test/resources/book/part3/chapter11/ipam.ttc";
    // public static final String FONT = "c:/windows/fonts/msgothic.ttc";

    public static void main(String[] agrs) throws Exception {
        new Listing_11_02_TTCExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        PdfFont font;
        TrueTypeCollection coll = new TrueTypeCollection(FONT, PdfEncodings.IDENTITY_H);
        for (int i = 0; i < coll.getTTCSize(); i++) {
            font = PdfFont.createFont(pdfDoc, coll.getFontByTccIndex(i), PdfEncodings.IDENTITY_H, true);
            doc.add(new Paragraph("font " + i + ": " + coll.getFontByTccIndex(i).getFontNames().getFontName())
                    .setFont(font).setFontSize(12));
            doc.add(new Paragraph("Rash\u00f4mon")
                    .setFont(font).setFontSize(12));
            doc.add(new Paragraph("Directed by Akira Kurosawa")
                    .setFont(font).setFontSize(12));
            doc.add(new Paragraph("\u7f85\u751f\u9580")
                    .setFont(font).setFontSize(12));
            doc.add(new Paragraph("\n"));
        }
        doc.close();
    }
}
