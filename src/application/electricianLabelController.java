package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class electricianLabelController {

	private String email;
	
    @FXML
    private Label electricianContact;

    @FXML
    private Label electricianName;

    @FXML
    private javafx.scene.control.Label rating;

    @FXML
    private Button showmore;
    
    @FXML
    private Button Get;
    
    public void setEmail(String a)
    {
    	this.email = a;
    	
    }
    
    public int getProviderIdByContact(String contact) {
        String query = "SELECT ProviderId FROM Provider WHERE contact = ?";
        int providerId = -1;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, contact);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                providerId = rs.getInt("ProviderId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return providerId;
    }
    
    
    
    @FXML
    void goToTask(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/postTask.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the FXML file
            postTaskController controller = loader.getController();
            controller.setEmail(email);
            
            // Pass data to the controller
            controller.setLabel(electricianName.getText(), electricianContact.getText(), rating.getText());
            int id = getProviderIdByContact(electricianContact.getText());
            controller.setproviderid(id);

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    

    @FXML
    void showMore(ActionEvent event) {
        // Assuming you have the provider ID
       // Replace this with the actual provider ID

        byte[] pdfData = PDFService.getPDF(getProviderIdByContact(electricianContact.getText()));
        if (pdfData != null) {
            File pdfFile = FIleUtil.createTempPDFFile(pdfData);
            PDFViewer.openPDF(pdfFile);
        } else {
        	 Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Login");
             alert.setHeaderText(null);
             alert.setContentText("Certification not found");
             alert.showAndWait();
        }
    }
    public void setName(String name) {
    	electricianName.setText(name);
    }
    public void setContact(String name) {
    	electricianContact.setText(name);
    }
    public void setRating(int r) {
        // Ensure the rating is within the valid range of 0 to 100
        if (r < 0) r = 0;
        if (r > 100) r = 100;

        // Calculate the rating
        double rating1 = (r / 100.0) * 5.0;

        // Convert the rating to a formatted string
        String ratingStr = String.format("%.1f", rating1);

        // Set the rating text
      
        rating.setText(ratingStr);
    }
    

}
