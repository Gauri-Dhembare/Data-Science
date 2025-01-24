import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String username, password;
            username = request.getParameter("Username");
            password = request.getParameter("Password");

            // Use a secure hashing algorithm to hash the password before storing it
            String hashedPassword = hashPassword(password);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sam", "root", "Gauri@2132");

            // Check if the user already exists before inserting
            if (!userExists(con, username)) {
                String query = "INSERT INTO table1 VALUES (?, ?)";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, hashedPassword);
                    ps.executeUpdate();
                }
                con.close();
                out.print("Record saved successfully!");
                RequestDispatcher rd = request.getRequestDispatcher("about.html");
                rd.forward(request, response);
            } else {
                out.print("User already exists...PLEASE ENTER VALID USERNAME..!");
            }
        } catch (Exception e) {
            // Log the exception or display a user-friendly error message
            out.println("An error occurred: " + e.getMessage());
        }
    }

    // Hash the password using a secure hashing algorithm (e.g., BCrypt)
    private String hashPassword(String password) {
        // Implement password hashing logic (e.g., using BCrypt)
        // Example: return BCrypt.hashpw(password, BCrypt.gensalt());
        return password;
    }

    // Check if the user already exists in the database
    private boolean userExists(Connection con, String username) throws SQLException {
        String query = "SELECT * FROM table1 WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Return true if a record with the username already exists
            }
        }
    }
}
