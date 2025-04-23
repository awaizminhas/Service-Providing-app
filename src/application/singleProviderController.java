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
import javafx.stage.Stage;

public class singleProviderController {

    @FXML
    private Button Get;

    @FXML
    private Label ProviderContact;

    @FXML
    private Label ProviderEmail; // Removed static keyword

    @FXML
    private Label ProviderName;

    private int id;

    public void setId(int a) {
        this.id = a;
    }

    public void setLabels(String c, String p, String n) {
        ProviderContact.setText(c);
        ProviderEmail.setText(p);
        ProviderName.setText(n);  
    }

    public int getProviderByEmail() {
        int providerId = -1; // Default value if no provider is found

        String query = "SELECT ProviderId FROM Provider WHERE email = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, ProviderEmail.getText());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                providerId = rs.getInt("ProviderId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return providerId;
    }

    @FXML
    void goToTask(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/ReviewandRating.fxml"));
            Parent root = loader.load();

            ReviewandRatingController controller = loader.getController();
            controller.setId(getProviderByEmail(), id);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
