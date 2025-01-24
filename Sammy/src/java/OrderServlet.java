import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve parameters from the request
            String ProductName = request.getParameter("ProductName");
            String Quantity = request.getParameter("Quantity");
            String Address = request.getParameter("Address");

            // Load the JDBC driver (assuming you are using MySQL Connector/J)
            Class.forName("com.mysql.jdbc.Driver");

            // Replace 'root' and 'Gauri@2132' with your actual database credentials
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sam", "root", "Gauri@2132");

            String action = request.getParameter("action");
            // Define the SQL query for retrieving orders
            if ("CONFIRM ORDER".equals(action)) {
                // Define the SQL query for inserting payments
                String insertQuery = "insert into orders(ProductName, Quantity, Address) VALUES (?, ?, ?)";
                PreparedStatement insertPs = con.prepareStatement(insertQuery);

                // Set parameters in the prepared statement
                insertPs.setString(1, ProductName);
                insertPs.setString(2, Quantity);
                insertPs.setString(3, Address);

                // Execute the query for inserting payments
                insertPs.executeUpdate();
            } else if ("CANCEL ORDER".equals(action)) {
                // Define the SQL query for canceling payments (delete the current payment)
                String cancelQuery = "DELETE FROM orders WHERE ProductName = ? AND Quantity = ? AND Address = ?";
                PreparedStatement cancelPs = con.prepareStatement(cancelQuery);

                // Set parameters in the prepared statement
                cancelPs.setString(1, ProductName);
                cancelPs.setString(2, Quantity);
                cancelPs.setString(3,Address );

                // Execute the query for canceling payments
                cancelPs.executeUpdate();
            } else if ("RESET ORDERS".equals(action)) {
                // Define the SQL query for resetting payments (clearing the Payments table)
                String resetQuery = "DELETE FROM orders";
                PreparedStatement resetPs = con.prepareStatement(resetQuery);

                // Execute the query for resetting payments
                resetPs.executeUpdate();
            }

            // Redirect the user to the payment display page
            response.sendRedirect("OrderDisplayServlet");

            // Close the database connection
            con.close();
            out.println("</body></html>");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Print the exception details to the client (for debugging)
            out.println(e);
        }
    }
}
