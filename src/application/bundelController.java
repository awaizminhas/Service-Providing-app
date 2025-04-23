package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class bundelController {

    @FXML
    private Label description;

    @FXML
    private Label taskId;
    
    public void setLabels(String d, int t) {
        description.setText(d);
        taskId.setText(String.valueOf(t));
    }

}
