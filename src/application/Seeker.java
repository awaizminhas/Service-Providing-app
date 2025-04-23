package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Seeker {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String contact;
	private String address;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void saveToDatabase() {
        String sql = "INSERT INTO Seeker (firstName, lastName, email, address, contact, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, this.firstName);
            pstmt.setString(2, this.lastName);
            pstmt.setString(3, this.email);
            pstmt.setString(4, this.address);
            pstmt.setString(5, this.contact);
            pstmt.setString(6, this.password);
            pstmt.executeUpdate();
            System.out.println("Saved to database: " + this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
}
