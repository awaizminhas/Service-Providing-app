package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class providerProfileController {

    @FXML
    private Button getbtn;

    @FXML
    private HBox orderHolder;

    @FXML
    private Label provider;
    
    private static int id;

    @FXML
    void getYourOrders(ActionEvent event) {
    	Node[] nodes = new Node[getTotalTaskScheduleCount()];
        List<String> seekerName = getSeekerNamesByTaskId();
        List<String> seekerAddress = getSeekerAddressesWithJoin();
        List<String> taskdescription = getTaskDescriptionsWithJoin();
        List<String> taskDates = getTaskDatesAsString();

        for (int i = 0; i < nodes.length; i++) {
            try {
                if (i < seekerAddress.size() && i < seekerName.size() && i < taskDates.size() && i < taskdescription.size()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("views/order.fxml"));
                    nodes[i] = loader.load();

                    orderController controller = loader.getController();
                    controller.setLabels(seekerAddress.get(i), seekerName.get(i), taskDates.get(i), taskdescription.get(i));

                    orderHolder.getChildren().add(nodes[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void setId(int a) {
	    this.id = a;
	   
	}
    public static List<String> getTaskDatesAsString() {
        String query = "SELECT taskDate FROM taskSchedule WHERE providerid = ?";
        List<String> taskDatesList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id); // Set the providerid parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Date taskDate = rs.getDate("taskDate");
                    String taskDateString = dateFormat.format(taskDate);
                    taskDatesList.add(taskDateString);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskDatesList;
    }
	public static int getTotalTaskScheduleCount() {
        String query = "SELECT COUNT(*) FROM taskSchedule";
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
	
	public static List<String> getSeekerNamesByTaskId() {
        String query = "SELECT s.firstName, s.lastName " +
                       "FROM taskSchedule ts " +
                       "JOIN Seeker s ON ts.seekerid = s.seekerId " +
                       "WHERE ts.providerid = ?";
        List<String> seekerNames = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                seekerNames.add(firstName + " " + lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seekerNames;
    }
	
	public static List<String> getTaskDescriptionsWithJoin() {
	    String query = "SELECT t.description " +
	                   "FROM taskSchedule ts " +
	                   "JOIN task t ON ts.taskId = t.taskId " +
	                   "JOIN Provider p ON ts.providerid = p.ProviderId " +
	                   "WHERE ts.providerid = ?";
	                  
	    List<String> descriptionsList = new ArrayList<>();

	    try (Connection conn = Database.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	         
	         pstmt.setInt(1, id); 

	         try (ResultSet rs = pstmt.executeQuery()) {
	             while (rs.next()) {
	                 String description = rs.getString("description");
	                 descriptionsList.add(description);
	             }
	         }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return descriptionsList;
	}
	
	
	public static List<String> getSeekerAddressesWithJoin() {
        String query = "SELECT s.address " +
                       "FROM taskSchedule ts " +
                       "JOIN Seeker s ON ts.seekerid = s.seekerId " +
                       "WHERE ts.providerid = ?";
        List<String> addressesList = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id); // Set the providerid parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String address = rs.getString("address");
                    addressesList.add(address);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressesList;
    }
}

	
	
	
	
	
	
	
  

  
