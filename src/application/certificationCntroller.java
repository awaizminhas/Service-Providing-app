package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class certificationCntroller {
	
	private int id;
	
    @FXML
    private Button addbtn;
    
    public void setId(int a)
    {
    	this.id = a;
    }
    
    @FXML
    void add(ActionEvent event) {
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
          try {
	        	
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/Providerprofile.fxml"));
	            Parent root = loader.load();
	            providerProfileController controller = loader.getController();
	            controller.setId(id);
	            

	            // Create a new scene with the loaded FXML
	            Scene scene = new Scene(root);

	            // Get the current stage
	            Stage stage1 = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

	            // Set the new scene
	            stage1.setScene(scene);
	            stage1.show();

	            // Show login successful alert
	           

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

    
    }
    private void uploadPDFToDatabase(byte[] pdfData) {
        String query = "INSERT INTO Certifications (pdf_file,providerID ) VALUES (?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setBytes(1, pdfData);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            showAlert(AlertType.INFORMATION, "File Upload", "PDF file uploaded successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
