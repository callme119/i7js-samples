/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/*
 * This class is part of the white paper entitled
 * "Digital Signatures for PDF documents"
 * written by Bruno Lowagie
 *
 * For more info, go to: http://itextpdf.com/learn
 */
package com.itextpdf.samples.signatures.chapter03;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.signatures.CrlClient;
import com.itextpdf.signatures.CrlClientOnline;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.PdfSigner;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import static org.junit.Assert.fail;

public class C3_03_SignWithCRLDefaultImp extends C3_01_SignWithCAcert {
    public static final String SRC = "./src/test/resources/signatures/chapter03/hello.pdf";
    public static final String DEST = "./target/test/resources/signatures/chapter03/hello_cacert_crl_imp.pdf";

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("./src/test/resources/signatures/chapter03/key.properties"));
        String path = properties.getProperty("PRIVATE");
        char[] pass = properties.getProperty("PASSWORD").toCharArray();

        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance("pkcs12", provider.getName());
        ks.load(new FileInputStream(path), pass);
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, pass);
        Certificate[] chain = ks.getCertificateChain(alias);
        List<CrlClient> crlList = new ArrayList<CrlClient>();
        crlList.add(new CrlClientOnline());
        C3_03_SignWithCRLDefaultImp app = new C3_03_SignWithCRLDefaultImp();
        app.sign(SRC, DEST, chain, pk, DigestAlgorithms.SHA256, provider.getName(), PdfSigner.CryptoStandard.CMS, "Test", "Ghent",
                crlList, null, null, 0);
    }

    @Test
    public void runTest() throws IOException, InterruptedException, GeneralSecurityException {
        C3_03_SignWithCRLDefaultImp.main(null);

        String[] resultFiles = new String[]{"hello_cacert_crl_imp.pdf"};

        String destPath = String.format(outPath, "chapter03");
        String comparePath = String.format(cmpPath, "chapter03");

        String[] errors = new String[resultFiles.length];
        boolean error = false;

        HashMap<Integer, List<Rectangle>> ignoredAreas = new HashMap<Integer, List<Rectangle>>() { {
            put(1, Arrays.asList(new Rectangle(36, 648, 200, 100)));
        }};

        for (int i = 0; i < resultFiles.length; i++) {
            String resultFile = resultFiles[i];
            String fileErrors = checkForErrors(destPath + resultFile, comparePath + "cmp_" + resultFile, destPath, ignoredAreas);
            if (fileErrors != null) {
                errors[i] = fileErrors;
                error = true;
            }
        }

        if (error) {
            fail(accumulateErrors(errors));
        }
    }
}