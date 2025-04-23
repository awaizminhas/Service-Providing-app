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

public class DisputeResolutionController {

	private String email;
	
	 @FXML
	private Button backbtn;
	public void setEmail(String a)
	{
		this.email = a;
	}

    @FXML
    private TextField DisputeDecription;

    @FXML
    private Button Enter;
   

    @FXML
    void EnterDispute(ActionEvent event) {
        String description = DisputeDecription.getText().trim(); // Get the description text

        if (description.isEmpty()) {
            // Show alert if the description is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Description");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a description for the dispute.");
            alert.showAndWait();
        } else {
            // Show alert if the description is not empty
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dispute Submitted");
            alert.setHeaderText(null);
            alert.setContentText("Mediator will reply shortly.");
            alert.showAndWait();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("views/seekerHome.fxml"));
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
    @FXML
    private void goBack() {
        try {
            // Load the other FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/seekerHome.fxml"));
            Parent root = loader.load();
            
            seekerHomeController controller = loader.getController();
            controller.setEmail(email);

            // Get the current stage
            Stage stage = (Stage) backbtn.getScene().getWindow();

            // Set the scene to the other page
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
