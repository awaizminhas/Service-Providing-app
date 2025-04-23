package application;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Providerlogin {

    @FXML
    private Button ProviderLogin;

    @FXML
    private Button ProviderSignup; // Add the signup button

    @FXML
    private TextField ProviderPassword;

    @FXML
    private TextField ProviderUsername;

    
    public int getProviderId(String email) {
        String query = "SELECT ProviderId FROM Provider WHERE email = ?";
        int providerId = -1; // Default value if no provider ID is found

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                providerId = rs.getInt("ProviderId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return providerId;
    }
    public static boolean checkPassword(String email, String password) {
        String query = "SELECT password FROM Provider WHERE email = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Check if the provided password matches the stored password
                    return storedPassword.equals(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If no matching email is found or an exception occurs, return false
        return false;
    }
    
    @FXML
    void ProviderLogin(ActionEvent event) {
    	
    	 if (checkPassword(ProviderUsername.getText(), ProviderPassword.getText())) {
    	        try {
    	        	 Alert alert = new Alert(AlertType.INFORMATION);
     	            alert.setTitle("Login");
     	            alert.setHeaderText(null);
     	            alert.setContentText("Login successful!");
     	            alert.showAndWait();
    	            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/certification.fxml"));
    	            Parent root = loader.load();
    	            certificationCntroller controller = loader.getController();
    	            controller.setId(getProviderId(ProviderUsername.getText()));
    	           

    	            // Create a new scene with the loaded FXML
    	            Scene scene = new Scene(root);

    	            // Get the current stage
    	            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

    	            // Set the new scene
    	            stage.setScene(scene);
    	            stage.show();

    	            // Show login successful alert
    	           

    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    } else {
    	        // Show login failed alert
    	        Alert alert = new Alert(AlertType.ERROR);
    	        alert.setTitle("Login");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Invalid username or password.");
    	        alert.showAndWait();
    	    }
    }
    

    @FXML
    void ProviderSignup(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/Providersignup.fxml"));
             Parent root = loader.load();

             // Get the controller associated with the taskSchedule.fxml file
      
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
}
