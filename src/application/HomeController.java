package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button Provider;

    @FXML
    private Button Seeker;

    @FXML
    void CallProvider(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/ProviderLogin.fxml"));
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

    @FXML
    void CallSeeker(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainView.fxml"));
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
