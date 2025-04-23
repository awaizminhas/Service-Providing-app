package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class taskScheduleController {

	private int sid;
	private int pid;
	private int tid;
	
    @FXML
    private DatePicker date;

    @FXML
    private Button post;
    
    
    public void setId(int a, int b, int c)
    {
    	this.sid = a;
    	this.pid = b;
    	this.tid = c;
    }
   
    

    @FXML
    void postTask(ActionEvent event) {
    	 String query = "INSERT INTO taskSchedule (taskId, seekerid, providerid, taskDate) VALUES (?, ?, ?, ?)";
    	 LocalDate taskDate = date.getValue();
    	 Date taskDateConverted = Date.valueOf(taskDate);
    	 
    	 
         try (Connection conn = Database.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(query)) {

             pstmt.setInt(1, tid);
             pstmt.setInt(2, sid);
             pstmt.setInt(3, pid);
             pstmt.setDate(4, taskDateConverted);

             int affectedRows = pstmt.executeUpdate();

             if (affectedRows > 0) {
                 System.out.println("Task schedule added successfully.");
             } else {
                 System.out.println("Adding task schedule failed.");
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
    

}
