package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class PDFViewer {
    public static void openPDF(File file) {
        if (file != null && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
