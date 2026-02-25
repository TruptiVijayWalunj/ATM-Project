package Additional;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/additional")
public class additional extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank"; // Change DB name if different
    private static final String DB_USER = "root"; // Change to your MySQL username
    private static final String DB_PASSWORD = "trupti08"; // Change to your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get card number and pin from the form
        String religion = request.getParameter("Religion");
        String income = request.getParameter("income");
        String occupation = request.getParameter("occupation");
        String pannumber= request.getParameter("pannumber");
        String adharno = request.getParameter("adharno");
        String seniorcitizen = request.getParameter(" seniorcitizen");
        
       // Database connection and insertion
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish Connection
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Insert Query
            String sql = "INSERT INTO additional(Religion, income, occupation ,pannumber ,adharno,seniorcitizen) VALUES (?, ?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "Religion");
            pst.setString(2, "income");
            pst.setString(3, "occupation");
            pst.setString(4,  "pannumber");
            pst.setString(5, "adharno" );
            pst.setString(6, "seniorcitizen" );
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                out.println("<h3 style='color:green;'>additinal registered successfully!</h3>");
            } else {
                out.println("<h3 style='color:red;'>Data insertion failed.</h3>");
            }

            // Close resources
            pst.close();
            con.close();
        } catch (ClassNotFoundException e) {
            out.println("<h3 style='color:red;'>JDBC Driver not found!</h3>");
            e.printStackTrace();
        } catch (SQLException e) {
            out.println("<h3 style='color:red;'>Database Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}