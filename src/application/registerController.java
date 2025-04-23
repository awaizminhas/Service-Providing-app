package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class registerController {

    @FXML
    private Button Done;

    @FXML
    private TextField address;

    @FXML
    private TextField contact;

    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField pass;
    @FXML
    private Button member;
    @FXML
    private TextField repass;

    @FXML
    void done(ActionEvent event) {
    	 String firstName = fname.getText();
         String lastName = lname.getText();
         String emailText = email.getText();
         String addressText = address.getText();
         String contactText = contact.getText();
         String password = pass.getText();
         String rePassword = repass.getText();

         // Validate passwords
         if (!password.equals(rePassword) ) {
             // Create an alert
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Password Mismatch");
             alert.setHeaderText(null);
             alert.setContentText("Passwords do not match!");

             // Show the alert
             alert.showAndWait();
             return;
         }

         // Create a new Seeker object
         Seeker seeker = new Seeker();
         seeker.setFirstName(firstName);
         seeker.setLastName(lastName);
         seeker.setEmail(emailText);
         seeker.setAddress(addressText);
         seeker.setContact(contactText);
         seeker.setPassword(password);

         
         seeker.saveToDatabase();
         
         try {
             // Load the login.fxml file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
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
    @FXML
    void goToLogin(ActionEvent event) {
    	try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
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
