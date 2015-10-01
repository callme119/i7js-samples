package com.itextpdf.samples.book.chapter01;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class Listing_01_07_HelloWorldMirroredMargins extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_01_07_HelloWorldMirroredMargins/Listing_01_07_HelloWorldMirroredMargins.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_01_07_HelloWorldMirroredMargins().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize customPageSize = PageSize.A5.clone().setMargins(108, 72, 180, 36);
        // TODO document.setMarginMirroring(true);
        Document doc = new Document(pdfDoc, customPageSize);

        doc.add(new Paragraph(
                "The left margin of this odd page is 36pt (0.5 inch); " +
                        "the right margin 72pt (1 inch); " +
                        "the top margin 108pt (1.5 inch); " +
                        "the bottom margin 180pt (2.5 inch)."));
        Paragraph paragraph = new Paragraph().setHorizontalAlignment(Property.HorizontalAlignment.JUSTIFIED);
        for (int i = 0; i < 20; i++) {
            paragraph.add("Hello World! Hello People! " +
                    "Hello Sky! Hello Sun! Hello Moon! Hello Stars!");
        }
        doc.add(paragraph);
        doc.add(new Paragraph(
                "The right margin of this even page is 36pt (0.5 inch); " +
                        "the left margin 72pt (1 inch)."));

        //Close document
        doc.close();
    }
}
