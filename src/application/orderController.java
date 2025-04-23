package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class orderController {

    @FXML
    private Label seekerAddress;

    @FXML
    private Label seekerName;

    @FXML
    private Label taskDate;
    
    @FXML
    private Label description;
    
    
    public void setLabels(String a, String b, String c, String d)
    {
    	seekerAddress.setText(a);
    	seekerName.setText(b);
    	taskDate.setText(c);
    	description.setText(d);
    }
    
    

}
