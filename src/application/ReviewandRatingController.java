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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ReviewandRatingController {
    
	private int providerid;
	private int seekerid;
	
    @FXML
    private ToggleGroup Rating;
    public String getSeekerEmailById() {
        String email = null;
        String query = "SELECT email FROM Seeker WHERE seekerid = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, seekerid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }

    @FXML
    private TextField ReviewDescription;

    @FXML
    private RadioButton rbutton1;

    @FXML
    private RadioButton rbutton2;

    @FXML
    private RadioButton rbutton3;

    @FXML
    private RadioButton rbutton4;

    @FXML
    private RadioButton rbutton5;
    
    public void setId(int a,int b)
    {
    	this.providerid =a;
    	this.seekerid = b;
    }
    
    int Ratings;
    @FXML
    void getrating(ActionEvent event) {
if(rbutton1.isSelected())
{
	Ratings=1;
}
if(rbutton2.isSelected())
{
	Ratings=2;
}
if(rbutton3.isSelected())
{
	Ratings=3;
}
if(rbutton4.isSelected())
{
	Ratings=4;
}
if(rbutton5.isSelected())
{
	Ratings=5;
}



    }

    @FXML
    void ConfirmReviewanndRating(ActionEvent event) {
    	String query = "INSERT INTO Review (SeekerId, Rating, feedback, ProviderId) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, seekerid);
            pstmt.setInt(2, Ratings);
            pstmt.setString(3, ReviewDescription.getText());
            pstmt.setInt(4, providerid);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/seekerHome.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the taskSchedule.fxml file
            seekerHomeController controller = loader.getController();
            controller.setEmail(getSeekerEmailById());
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
