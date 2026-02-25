package Data;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class data
 */
@WebServlet("/Data")
public class data extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public data() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String amount = request.getParameter("id");
        String bill_no = request.getParameter("deposite");
        String withdraw = request.getParameter("withdraw");
        String balance= request.getParameter("balance");

        

        
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Data", "root", "trupti08"
            );

            // Insert query
            String query = "INSERT INTO statement (id, deposite,withdraw,balance) VALUES (1,200,300,500)";
            PreparedStatement ps = con.prepareStatement(query);

            // Set parameters
            ps.setString(1,amount);
            ps.setString(2, bill_no);

            // Execute update
            int status = ps.executeUpdate();
            if (status > 0) {
                out.print("<p style='color:green;'>Record added successfully.</p>");
            } else {
                out.print("<p style='color:red;'>Error adding record.</p>");
            }

            // Redirect to a page (optional)
            request.getRequestDispatcher("transaction.html").include(request, response);

        } catch (Exception e) {
            out.print("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }

	
}