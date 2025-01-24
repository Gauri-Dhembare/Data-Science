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

@WebServlet("/PaymentDisplayServlet1")
public class PaymentDisplayServlet1 extends HttpServlet {

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
            String selectQuery = "SELECT Rupees, Mode FROM Payments";
            PreparedStatement selectPs = con.prepareStatement(selectQuery);

            // Execute the query for selecting payments
            ResultSet rs = selectPs.executeQuery();

            // Display the payments in the servlet response
            out.println("<html><head><title>Payment Display</title></head><body>");
            out.println("<h2>Payments:</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Rupees</th><th>Mode</th></tr>");

            while (rs.next()) {
                String rupeesDB = rs.getString("Rupees");
                String modeDB = rs.getString("Mode");
                

                out.println("<tr>");
                out.println("<td>" + rupeesDB + "</td>");
                out.println("<td>" + modeDB + "</td>");
                
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            // Print the exception details to the client (for debugging)
            out.println(e);
        }
    }
}
