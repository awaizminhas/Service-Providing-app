package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class taskBundlingController {

    private List<String> selectedTasks = new ArrayList<>();
    private int counter = 1;
    private int id;

    @FXML
    private Button addbtn;
    @FXML
    private Button backbtn;

    @FXML
    private HBox taskHolder;

    @FXML
    private ChoiceBox<String> tasks;

    public String getSeekerEmailById(int id) {
        String email = null;
        String query = "SELECT email FROM Seeker WHERE seekerId = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }
    
    public void setId(int i) {
        this.id = i;
    }

    @FXML
    void addTask(ActionEvent event) {
        String selectedTaskDescription = tasks.getValue();
        selectedTasks.add(selectedTaskDescription);
        updateTaskHolder();
    }

    private void updateTaskHolder() {
        taskHolder.getChildren().clear();
        Node[] nodes = new Node[counter];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("views/bundle.fxml"));
                nodes[i] = loader.load();

                bundelController controller = loader.getController();
                controller.setLabels(selectedTasks.get(i), counter);
               

               
                taskHolder.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        counter++;
    }

    public void populateTasks() {
        String query = "SELECT t.description FROM task t "
                + "JOIN seeker_task st ON st.taskId = t.taskId "
                + "WHERE st.seekerId = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            ObservableList<String> taskDescriptions = FXCollections.observableArrayList();
            while (rs.next()) {
                taskDescriptions.add(rs.getString("description"));
            }

            tasks.setItems(taskDescriptions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goBack() {
        try {
            // Load the other FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/seekerHome.fxml"));
            Parent root = loader.load();
            
            seekerHomeController controller = loader.getController();
            controller.setEmail(getSeekerEmailById(id));

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
