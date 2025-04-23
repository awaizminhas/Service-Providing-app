package application;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadController {

    @FXML
    private void handleUploadButtonAction() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PDF File");

        // Set extension filter for PDF files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                // Read the PDF file into a byte array
                FileInputStream fis = new FileInputStream(file);
                byte[] pdfData = new byte[(int) file.length()];
                fis.read(pdfData);
                fis.close();

                // Upload the PDF to the database
                uploadPDFToDatabase(pdfData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadPDFToDatabase(byte[] pdfData) {
        String query = "INSERT INTO Certifications (pdf_file) VALUES (?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setBytes(1, pdfData);
            pstmt.executeUpdate();

            System.out.println("PDF file uploaded successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
