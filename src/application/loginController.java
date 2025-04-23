package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class loginController {

    @FXML
    private Button Done;
    @FXML
    private Button donthave;
    @FXML
    private TextField email;

    @FXML
    private ImageView loginimg;

    @FXML
    private TextField password;

    @FXML
    public void loggedin(ActionEvent event) {
        

        if (validateLogin(email, password)) {
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Login Successful!");
            alert.showAndWait();
            
            try {
                // Load the new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("views/seekerHome.fxml"));
                Parent root = loader.load();
                
                seekerHomeController controller = loader.getController();
                controller.setEmail(email.getText());
                // Create a new stage for the new page
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("New Page");

                // Close the current stage
                Stage currentStage = (Stage) email.getScene().getWindow();
                currentStage.close();

                // Show the new stage
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Login failed
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password.");
            alert.showAndWait();
        }
    }

    private boolean validateLogin(TextField email2, TextField password2) {
        String query = "SELECT * FROM Seeker WHERE email = ? AND password = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

        	String email = email2.getText();
        	String password = password2.getText();
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // User exists
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    @FXML
    void goToRegister(ActionEvent event) {
    	try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainView.fxml"));
            Parent root = loader.load();
            
           

            // Get the current stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
