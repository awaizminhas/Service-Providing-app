package application;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProviderProfile {

    @FXML
    private Button AddCertificate;

    @FXML
    private Label Address;

    @FXML
    private TableColumn<?, ?> Contact;

    @FXML
    private TableColumn<?, ?> Date;

    @FXML
    private Label Email;

    @FXML
    private Label Phone;

    @FXML
    private TableView<?> SeekerTable;

    @FXML
    private TableColumn<?, ?> Seeker_ID;

    @FXML
    private TableColumn<?, ?> Seeker_Name;

}