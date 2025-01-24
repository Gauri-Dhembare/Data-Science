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


public class OrderDisplayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load the JDBC driver (assuming you are using MySQL Connector/J)
            Class.forName("com.mysql.jdbc.Driver");

            // Replace 'agency1', 'root', and '' with your actual database credentials
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sam", "root", "Gauri@2132");

            // Define the SQL query for retrieving payments
            String selectQuery = "select ProductName, Quantity, Address from orders";
            PreparedStatement selectPs = con.prepareStatement(selectQuery);

            // Execute the query for selecting payments
            ResultSet rs = selectPs.executeQuery();

            // Display the payments in the servlet response
            out.println("<html><head><title>Order Display</title></head><body>");
            out.println("<h2>Orders:</h2>");

            while (rs.next()) {
                String ProductNameDB = rs.getString("ProductName");
                String QuantityDB = rs.getString("Quantity");
                String AddressDB = rs.getString("Address");

                out.println("<p><strong>ProductName:</strong> " + ProductNameDB + "</p>");
                out.println("<p><strong>Quantity:</strong> " + QuantityDB + "</p>");
                out.println("<p><strong>Address:</strong> " + AddressDB + "</p>");
                out.println("<hr>");
            }

            out.println("</body></html>");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Print the exception details to the client (for debugging)
            out.println(e);
        }
    }
}
