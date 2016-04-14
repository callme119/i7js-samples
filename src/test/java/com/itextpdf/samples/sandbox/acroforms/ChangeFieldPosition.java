/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/22258598/itextsharp-move-acrofield
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.util.Map;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ChangeFieldPosition extends GenericTest {
    public static final String SRC = "./src/test/resources/pdfs/state.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/change_field_position.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeFieldPosition().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        PdfFormField fieldName = fields.get("timezone2");
        PdfWidgetAnnotation annotationName = fieldName.getWidgets().get(0);
        PdfArray annotationRect = annotationName.getRectangle();
        annotationRect.set(2, new PdfNumber(annotationRect.getAsNumber(2).getFloatValue() - 10f));
        pdfDoc.close();
    }
}
