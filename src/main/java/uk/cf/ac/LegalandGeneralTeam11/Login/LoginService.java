package uk.cf.ac.LegalandGeneralTeam11.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

//@Service
public class LoginService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoginService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Authenticates a user by verifying the provided username and password against the database.
     *
     * @param username The username to be authenticated.
     * @param password The password associated with the username.
     * @return {@code true} if the username and password match an entry in the database; otherwise, {@code false}.
     */
    public boolean authenticate(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, username, password);
        // Check if count is greater than 0, indicating a match exists in the database
        if (count > 0) {
            return true;
        } else {
            // Handle the case when no matching entry is found in the database
            System.out.println("No matching user entry found in the database for provided credentials.");
            return false; // Authentication failed
        }
    }
}

