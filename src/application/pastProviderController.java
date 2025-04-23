package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class pastProviderController {

	private static int id;
	
	
    @FXML
    private Button getProviders;

    @FXML
    private VBox providers;
    
    public void setId(int a)
    {
    	this.id = a;
    }
    public List<String> getFullNames() {
        String query = "SELECT p.firstName, p.lastName " +
                       "FROM Provider p " +
                       "JOIN taskSchedule ts ON ts.providerid = p.ProviderId " +
                       "WHERE ts.seekerid = ?";
        List<String> fullNamesList = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                fullNamesList.add(fullName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullNamesList;
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
    
    
    public List<String> getEmails() {
        String query = "SELECT p.email " +
                       "FROM Provider p " +
                       "JOIN taskSchedule ts ON ts.providerid = p.ProviderId " +
                       "WHERE ts.seekerid = ?";
        List<String> emailList = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                emailList.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailList;
    }

    public List<String> getContacts() {
        String query = "SELECT p.contact " +
                       "FROM Provider p " +
                       "JOIN taskSchedule ts ON ts.providerid = p.ProviderId " +
                       "WHERE ts.seekerid = ?";
        List<String> contactList = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                contactList.add(rs.getString("contact"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;
    }
    
    
    @FXML
    void getProviders(ActionEvent event) {
    	 Node[] nodes = new Node[getTotalTaskScheduleCount()];
         List<String> electricianNames = getFullNames();
         List<String> electricianContacts = getContacts();
         List<String> electricianEmails = getEmails();

         for (int i = 0; i < nodes.length; i++) {
             try {
                 final int j = i;
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("views/singleProvider.fxml"));
                 nodes[i] = loader.load();

                 singleProviderController controller = loader.getController();
                 controller.setLabels(electricianContacts.get(i), electricianEmails.get(i), electricianNames.get(i));
                 controller.setId(id);

                
                 providers.getChildren().add(nodes[i]);

             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }

}
