import java.io.*;
import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
@WebServlet("/FeedbackServlet1")
public class FeedbackServlet1 extends FeedbackDisplayServlet1 {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // Retrieve parameters from the request
            String name, feedback ;
            name = request.getParameter("Name");
            feedback = request.getParameter("Feedback");
           
          
            // Load the JDBC driver (assuming you are using MySQL Connector/J)
            Class.forName("com.mysql.jdbc.Driver");

            // Replace 'agency1', 'root', and '' with your actual database credentials
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sam", "root", "Gauri@2132");

            // Define the SQL query for inserting feedback
            String query = "insert into table2(Name, Feedback) values(?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            // Set parameters in the prepared statement
            ps.setString(1, name);
            ps.setString(2, feedback);
           

            // Execute the query
            ps.executeUpdate();

            // Close the database connection
            con.close();

            // Print a success message to the client
            out.print("Record saved successfully!");

            // Redirect the user to the home page (index.html)
            response.sendRedirect("FeedbackDisplayServlet1");
            

        } catch (Exception e) {
            // Print the exception details to the client (for debugging)
            out.println(e);
        }
    }
}
