package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class postTaskController {

	
	private int providerid ;
	private String email;
	
	
    @FXML
    private Label Name;

    @FXML
    private Label contact;

    @FXML
    private TextArea description;

    public void setproviderid(int a)
    {
    	this.providerid = a;
    }
    
    
    public void setEmail(String a)
    {
    	this.email =a;
    	
    }
    @FXML
    private Button schedulebtn;

    @FXML
    void goToTaskSchedule(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/taskSchedule.fxml"));
             Parent root = loader.load();

             // Get the controller associated with the taskSchedule.fxml file
             taskScheduleController controller = loader.getController();
             controller.setId(getSeekerIdByEmail(email), providerid,getTaskIdByDescription(description.getText()) ); // Pass email or other data if needed

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
    

    
    
    public int getSeekerIdByEmail(String email) {
        String query = "SELECT seekerId FROM Seeker WHERE email = ?";
        int seekerId = -1; // Default value if email is not found

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    seekerId = resultSet.getInt("seekerId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seekerId;
    }
    
    
    @FXML
    private Button postbtn;

    @FXML
    private Label rating;

   

    @FXML
    void postTask(ActionEvent event) {
    	 String taskDescription = description.getText(); // Get the task description from the TextArea

    	    // Check if the task description is not empty
    	    if (!taskDescription.isEmpty()) {
    	        // Insert the task description into the task table
    	        if (insertTaskDescription(taskDescription)) {
    	        	insertSeeker();
    	            System.out.println("Task description uploaded successfully.");
                    showAlert(AlertType.INFORMATION, "Success", "Task added successfully.");

        	    }
    	           
    	         else {
    	            System.out.println("Failed to upload task description.");
    	            // Handle the failure to upload the task description
    	        }
    	    } else {
    	        System.out.println("Task description is empty.");
    	        // Provide feedback to the user that the task description is required
    	        showAlert(AlertType.WARNING, "Empty Description", "Please enter a task description.");
    	    }

    }
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean insertTaskDescription(String description) {
        String query = "INSERT INTO task (description) VALUES (?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, description);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }
    public int getTaskIdByDescription(String description) {
        String query = "SELECT taskId FROM task WHERE description = ?";
        int taskId = -1; // Default value if description is not found

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, description);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    taskId = resultSet.getInt("taskId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskId;
    }
    private boolean insertSeeker()
    {
    	 String query = "INSERT INTO seeker_task (taskId,seekerId) VALUES (?, ?)";
         try (Connection connection = Database.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             preparedStatement.setInt(1, getTaskIdByDescription( description.getText()));
             preparedStatement.setInt(2, getSeekerIdByEmail(email));
             int rowsAffected = preparedStatement.executeUpdate();
             return rowsAffected > 0;
         } catch (SQLException e) {
             e.printStackTrace();
             return false;
         }
    }
    
    
    public void setLabel(String n, String c, String d) {
        Name.setText(n);
        contact.setText(c);
        rating.setText(d);
    }

	
	
	 
	 
    
    

}
