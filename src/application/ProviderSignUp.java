package application;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProviderSignUp implements Initializable{

    @FXML
    private TextField ProviderAddress;

    @FXML
    private TextField ProviderContact;

    @FXML
    private TextField ProviderEmail;

    @FXML
    private TextField ProviderFirstName;

    @FXML
    private TextField ProviderLastName;

    @FXML
    private TextField ProviderPassword;

    @FXML
    private Button ProviderSignup;

    @FXML
    private ChoiceBox<String> skill;

    @FXML
    void ProviderSignup(ActionEvent event) {
    	 if (ProviderFirstName.getText().isEmpty() || 
    		        ProviderLastName.getText().isEmpty() ||
    		        ProviderEmail.getText().isEmpty() ||
    		        ProviderAddress.getText().isEmpty() ||
    		        ProviderContact.getText().isEmpty() ||
    		        ProviderPassword.getText().isEmpty() ||
    		        skill.getValue() == null) {

    		        // Alert the user to fill in all details
    		        Alert alert = new Alert(AlertType.WARNING);
    		        alert.setTitle("Missing Information");
    		        alert.setHeaderText(null);
    		        alert.setContentText("Please fill in all fields.");
    		        alert.showAndWait();
    		    } else {
    	 String query = "INSERT INTO Provider (firstName, lastName, email, address, contact, password, Skill) VALUES (?, ?, ?, ?, ?, ?, ?)";

         try (Connection conn = Database.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(query)) {

             pstmt.setString(1, ProviderFirstName.getText());
             pstmt.setString(2, ProviderLastName.getText());
             pstmt.setString(3, ProviderEmail.getText());
             pstmt.setString(4, ProviderAddress.getText());
             pstmt.setString(5, ProviderContact.getText());
             pstmt.setString(6, ProviderPassword.getText());
             pstmt.setString(7, skill.getValue());

             pstmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     
    	
    	
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/ProviderLogin.fxml"));
             Parent root = loader.load();

          
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        skill.getItems().addAll("Electrical", "Plumbering", "Remodeling", "Maintenance","Security", "Cleaning", "Painting", "Landscaping"  );

		
	}
    }

