package AtmServlet;
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

@WebServlet("/AtmServlet")
public class AtmServlet extends HttpServlet {
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
        String name = request.getParameter("name");
        String fatherName = request.getParameter("father_name");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String atm = request.getParameter("atm");
        String pin = request.getParameter("pin");


        // Validate input
        if (atm == null || pin == null || atm.isEmpty() || pin.isEmpty()) {
            out.println("<h3 style='color:red;'>Card Number and PIN are required!</h3>");
            return;
        }

        // Database connection and insertion
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish Connection
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Insert Query
            String sql = "INSERT INTO login (name, father_name, dob, gender, email, address, city, atm, pin) VALUES (?, ?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, fatherName);
            pst.setString(3, dob);
            pst.setString(4, gender);
            pst.setString(5, email);
            pst.setString(6, address);
            pst.setString(7, city);
            pst.setString(8, atm);
            pst.setString(9, pin);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                out.println("<h3 style='color:green;'>application registered successfully!</h3>");
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