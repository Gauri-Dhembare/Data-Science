import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SubscriptionServlet1")
public class SubscriptionServlet1 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve parameters from the request
            String name, email;
            name = request.getParameter("Name");
            email = request.getParameter("Email");

            // Load the JDBC driver (assuming you are using MySQL Connector/J)
            Class.forName("com.mysql.jdbc.Driver");

            // Replace 'agency1', 'root', and '' with your actual database credentials
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sam", "root", "Gauri@2132");

            // Define the SQL query for inserting subscriptions
            String query = "insert into Subscribe(Name, Email) values(?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            // Set parameters in the prepared statement
            ps.setString(1, name);
            ps.setString(2, email);

            // Execute the query for inserting subscriptions
            ps.executeUpdate();

            // Close the database connection
            con.close();

            // Print a success message to the client
            out.print("Subscription successful!");

        } catch (Exception e) {
            // Print the exception details to the client (for debugging)
            out.println(e);
        }
    }
}
