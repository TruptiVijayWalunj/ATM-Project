package accountservlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Accounts")  // Ensure this matches the form action URL
public class Accounts extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("account/html");
        PrintWriter out = response.getWriter();

        String accountType = request.getParameter("accountType");
        String cardNumber = request.getParameter("cardNumber");
        String pin = request.getParameter("pin");
        String[] services = request.getParameterValues("services[]");

        String jdbcURL = "jdbc:mysql://localhost:3306/bank"; // Change to your DB
        String dbUser = "root"; // Change to your DB username
        String dbPassword = "trupti08"; // Change to your DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "trupti08");

            String sql = "INSERT INTO account (account_type, card_number, pin, Service_name) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (String service : services) {
                pstmt.setString(1, accountType);
                pstmt.setString(2, cardNumber);
                pstmt.setString(3, pin);
                pstmt.setString(4, service);
                pstmt.executeUpdate();
            }

            out.println("<h2>Account details stored successfully!</h2>");
            conn.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
