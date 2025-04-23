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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class seekerHomeController {

    private static String email;

    @FXML
    private Label seeker;

    @FXML
    private Label accountlabel;
    @FXML
    private Button goToReview;

    @FXML
    private Label addresslabel;

    @FXML
    private Button cleaningbtn;

    @FXML
    private Label detailslabel;

    @FXML
    private AnchorPane electricalbox;

    @FXML
    private AnchorPane electricalbox1;

    @FXML
    private AnchorPane electricalbox11;

    @FXML
    private AnchorPane electricalbox111;

    @FXML
    private AnchorPane electricalbox2;

    @FXML
    private AnchorPane electricalbox21;

    @FXML
    private AnchorPane electricalbox3;

    @FXML
    private AnchorPane electricalbox31;

    @FXML
    private Button electricalbtn;

    @FXML
    private Label historylabel;

    @FXML
    private Button landscapingbtn;

    @FXML
    private Button maintainancebtn;

    @FXML
    private Label orderslabel;

    @FXML
    private Button paintingbtn;

    @FXML
    private Button pumblingbtn;

    @FXML
    private Button remodelingbtn;

    @FXML
    private Button securitybtn;

    @FXML
    private Label transactionslabel;
    
    @FXML
    private Button bundle;
    @FXML
    void DisputeResolution(ActionEvent event) {

    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/DisputeResoution.fxml"));
             Parent root = loader.load();

             // Get the controller associated with the taskSchedule.fxml file
             DisputeResolutionController controller = loader.getController();
             controller.setEmail(email);
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

    public void setEmail(String a) {
        this.email = a;
        
    }

    @FXML
    void go(MouseEvent event) {

    }

    @FXML
    void goToCleaning(ActionEvent event) {

    }
    
    public static int getSeekerIdByEmail() {
        int seekerId = -1; // Default value if no seeker is found
        
        String query = "SELECT seekerId FROM Seeker WHERE email = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                seekerId = rs.getInt("seekerId");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return seekerId;
    }

    @FXML
    void goToElectrical(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/electricalView.fxml"));
            Parent root = loader.load();

            electricalController controller = loader.getController();
            controller.setEmail(email);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToLandscaping(ActionEvent event) {

    }

    @FXML
    void goToMaintainance(ActionEvent event) {

    }

    @FXML
    void goToPainting(ActionEvent event) {

    }

    @FXML
    void goToPumbling(ActionEvent event) {

    }

    @FXML
    void goToRemodeling(ActionEvent event) {

    }
    
    @FXML
    void gotoReview(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/pastProvide.fxml"));
             Parent root = loader.load();

             pastProviderController controller = loader.getController();
             controller.setId(getSeekerIdByEmail());

             Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }


    @FXML
    void goToSecurity(ActionEvent event) {

    }

    @FXML
    void scroll(ScrollEvent event) {

    }
    @FXML
    void taskBundling(ActionEvent event) {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("views/taskBundling.fxml"));
             Parent root = loader.load();

             taskBundlingController controller = loader.getController();
             controller.setId(getSeekerIdByEmail());
             controller.populateTasks();

             Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }

    	
    	
    }
}
