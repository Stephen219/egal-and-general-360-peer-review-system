package uk.cf.ac.LegalandGeneralTeam11.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepo implements UserRepositoryInter {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JdbcTemplate jdbc;
    private RowMapper<User> userMapper;


    public UserRepo(JdbcTemplate jdbc, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.jdbc = jdbc;
        setUsersMapper();
    }


    public void setUsersMapper() {
        this.userMapper = (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getLong("role_id"));

    }

    public User getUser(Long id) {
        String sql = "select * from users where Id = ?";
        return jdbc.queryForObject(sql, userMapper, id);
    }

    public List<User> getUsers() {
        String sql = "select * from users";
        return jdbc.query(sql, userMapper);
    }

    public User getUserEmail(String username) {
        String sql = "select * from users where username = ?";
        return jdbc.queryForObject(sql, userMapper, username);
    }

    public void encodePassword(User user) {
        String plainPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(encodedPassword);
        System.out.println(encodedPassword);
        System.out.println(plainPassword);
    }


    public void save(User user) {

        if (checkUserExists(user.getEmail(), user.getUsername())) {
            throw new RuntimeException("User already exists");
        }
        insert(user);

    }

    public void insert(User user) {
        encodePassword(user);
        System.out.println(user.getPassword());
        String userInsertSql =
                "insert into users " +
                        "(username, email, password, role_id,enabled)" +
                        " values (?,?,?,?,?)";

        jdbc.update(userInsertSql, user.getUsername(), user.getEmail(), user.getPassword(), user.getRoleId(), false);

    }

    public void updateUser(User user) {
        String userUpdateSql =
                "update users set " +
                        "username = ?, " +
                        "email = ?, " +
                        "password = ?, " +
                        "enabled = ? " +  // Add a space here
                        "where id = ?";
        jdbc.update(userUpdateSql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoleId(),
                user.getId());
    }


    public boolean checkUserExists(String email, String username) {
        String sql = "select count(*) from users where email = ? or username = ?";
        //String sql = "select count(*) from users where email = ?";
        int count = jdbc.queryForObject(sql, Integer.class, email, username);
        return count > 0;
    }


    public User getUserByUserName(String username) {
        String sql = "select * from users where username = ?";
        return jdbc.queryForObject(sql, userMapper, username);
    }

    public List<User> getUsersNotHavingFormThisYear() {
        String sql = "select * from users join 360forms on users.username =360forms.username where users.username not in (select username from 360forms where created_at = year(now()))";
        return jdbc.query(sql, userMapper);
    }


    /**
     * this ios now the method for activating the user account after registration
     * @param username
     * @return void
     * @throws RuntimeException
     */

    public void activateUser(String username) {
        String sql = "update users set enabled = true where username = ?";
        jdbc.update(sql, username);}


    /**
     * generateActivationToken
     * @param user
     * @return String
     */



    public String generateActivationToken(User user, String type) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate;

        if (type.equals("reset")) {
            // Set the token expiration to 30 minutes from now for reset type
            expiryDate = LocalDateTime.now().plusMinutes(30);
        } else {
            // Set the token expiration to 7 days from now for other types
            expiryDate = LocalDateTime.now().plusDays(7);
        }

        String sql = "INSERT INTO activation_tokens (email, token, expiry, type) VALUES (?, ?, ?,?)";
        jdbc.update(sql, user.getEmail(), token, expiryDate, type);

        return token;
    }



    /**
     * check whether the token is expired
     * @param token
     * @return boolean true if the token is expired   throw an exception if the token is expired
     */


    public boolean isTokenExpired(String token) {
        String sql = "select count(*) from activation_tokens where token = ? and expiry < now()";
        int count = jdbc.queryForObject(sql, Integer.class, token);
        if (count > 0) {
            throw new RuntimeException("Token expired");
        }
        return count > 0;
    }



    /**
     * check whther the user has already been activated
     *
     * throw an exception if the user has already been activated
     * return true if the token is used
     */


    public boolean isTokenUsed(String token) {
        String sql = "select count(*) from activation_tokens where token = ? and is_used = true";
        int count = jdbc.queryForObject(sql, Integer.class, token);
        if (count > 0) {
            throw new RuntimeException("Token already used");
        }
        return count > 0;}


    /**
     * getActivationToken method   throws an exception if the token is not found  and token is expired or used
     **/

    public TokenDto getActivationToken(String token) {
        String sql = "select * from activation_tokens where token = ?";
        List<TokenDto> tokens = jdbc.query(sql, (rs, rowNum) -> new TokenDto(
                rs.getLong("token_id"),
                rs.getString("email"),
                rs.getString("token"),
                rs.getTimestamp("expiry").toLocalDateTime(),
                rs.getBoolean("is_used"),
                rs.getString("type")
        ), token);
        if (tokens.isEmpty()) {
            throw new RuntimeException("Token not found");
        }
        return tokens.get(0);}

    /**
     * setTokenUsed method
     * @param token
     * @return void
     */

    public void setTokenUsed(String token) {
        String sql = "update activation_tokens set is_used = true where token = ?";
        jdbc.update(sql, token);}



    /**
     * getUserByEmail method   throws an exception if the user is not found
     * @param email
     * @return User and throw an exception if the user is not found
     */

    public User getUserByEmail(String email) throws RuntimeException {
        String sql = "select * from users where email = ?";
        List<User> users = jdbc.query(sql, userMapper, email);
        if (users.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return users.get(0);
    }



















}
