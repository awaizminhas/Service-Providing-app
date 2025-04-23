package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class electricalController{

	private String email;
	
	
	 public static String getSeekerNameByTaskId(int taskId) {
	        String query = "SELECT s.firstName, s.lastName " +
	                       "FROM taskSchedule ts " +
	                       "JOIN Seeker s ON ts.seekerid = s.seekerId " +
	                       "WHERE ts.taskId = ?";
	        String seekerName = null;

	        try (Connection conn = Database.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {

	            pstmt.setInt(1, taskId);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                String firstName = rs.getString("firstName");
	                String lastName = rs.getString("lastName");
	                seekerName = firstName + " " + lastName;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return seekerName;
	    }
	
    @FXML
    private VBox electrianHolder;
    
    @FXML
    private Button backbtn;

    @FXML
    private Button searchbtn;

    @FXML
    void search(ActionEvent event) {
    	 Node[] nodes = new Node[getElectricianCount()];
         List<String> electricianNames = getElectricianNames();
         List<String> electricianContacts = getElectricianContact();
         List<Integer> electricianRatings = getElectricianRatings();

         for (int i = 0; i < nodes.length; i++) {
             try {
                 final int j = i;
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("views/electrician.fxml"));
                 nodes[i] = loader.load();

                 electricianLabelController controller = loader.getController();
                 controller.setName(electricianNames.get(i));
                 controller.setRating(electricianRatings.get(i));
                 controller.setContact(electricianContacts.get(i));
                 controller.setEmail(email);

                
                 electrianHolder.getChildren().add(nodes[i]);

             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }
    public void setEmail(String email) {
        this.email = email;
       
  }
    public static int getElectricianCount() {
        String query = "SELECT COUNT(*) FROM Provider WHERE Skill = 'Electrical'";
        int count = 0;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static List<String> getElectricianNames() {
        String query = "SELECT firstName, lastName FROM Provider WHERE Skill = 'Electrical'";
        List<String> names = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                names.add(firstName + " " + lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return names;
    }

    public static List<String> getElectricianContact() {
        String query = "SELECT contact FROM Provider WHERE Skill = 'Electrical'";
        List<String> contacts = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String contact = rs.getString("contact");
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public static List<Integer> getElectricianRatings() {
        String query = "SELECT Positive_ratio FROM Provider WHERE Skill = 'Electrical'";
        List<Integer> ratings = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int rating = rs.getInt("Positive_ratio");
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratings;
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
