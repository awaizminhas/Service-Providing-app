package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FIleUtil {
    public static File createTempPDFFile(byte[] pdfData) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", ".pdf");
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(pdfData);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
