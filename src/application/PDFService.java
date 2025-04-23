package application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PDFService {
	
	private static int id ;
	public void setID(int i)
	{
		id = i;
	}
    public static byte[] getPDF(int providerId) {
    	
    	
        String query = "SELECT pdf_file FROM Certifications WHERE providerID = ?";
        byte[] pdfData = null;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, providerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("pdf_file");
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                pdfData = os.toByteArray();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return pdfData;
    }
}
