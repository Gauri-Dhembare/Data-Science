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

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Retrieve parameters from the request
            String rupees, mode, cardNumber;
            rupees = request.getParameter("Rupees");
            mode = request.getParameter("Mode");
            cardNumber = request.getParameter("CardNumber");

            // Load the JDBC driver (assuming you are using MySQL Connector/J)
            Class.forName("com.mysql.jdbc.Driver");

            // Replace 'agency1', 'root', and '' with your actual database credentials
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sam", "root", "Gauri@2132");

            // Check which button was clicked
            String action = request.getParameter("action");

            if ("CONFIRM PAYMENT".equals(action)) {
                // Define the SQL query for inserting payments
                String insertQuery = "insert into Payments(Rupees, Mode, CardNumber) values(?, ?, ?)";
                PreparedStatement insertPs = con.prepareStatement(insertQuery);

                // Set parameters in the prepared statement
                insertPs.setString(1, rupees);
                insertPs.setString(2, mode);
                insertPs.setString(3, cardNumber);

                // Execute the query for inserting payments
                insertPs.executeUpdate();
            } else if ("CANCEL PAYMENT".equals(action)) {
                // Define the SQL query for canceling payments (delete the current payment)
                String cancelQuery = "DELETE FROM Payments WHERE Rupees = ? AND Mode = ? AND CardNumber = ?";
                PreparedStatement cancelPs = con.prepareStatement(cancelQuery);

                // Set parameters in the prepared statement
                cancelPs.setString(1, rupees);
                cancelPs.setString(2, mode);
                cancelPs.setString(3, cardNumber);

                // Execute the query for canceling payments
                cancelPs.executeUpdate();
            } else if ("RESET PAYMENT".equals(action)) {
                // Define the SQL query for resetting payments (clearing the Payments table)
                String resetQuery = "DELETE FROM Payments";
                PreparedStatement resetPs = con.prepareStatement(resetQuery);

                // Execute the query for resetting payments
                resetPs.executeUpdate();
            }

            // Redirect the user to the payment display page
            response.sendRedirect("PaymentDisplayServlet1");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Print the exception details to the client (for debugging)
            out.println(e);
        }
    }
}
