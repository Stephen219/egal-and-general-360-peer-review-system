package uk.cf.ac.LegalandGeneralTeam11.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo implements  UserRepositoryInter{

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JdbcTemplate jdbc;
    private RowMapper<User> userMapper;


    public UserRepo(JdbcTemplate jdbc, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.jdbc = jdbc;
        setUsersMapper();
    }

//    public UserRepo(PasswordEncoder passwordEncoder, JdbcTemplate jdbc) {
//        this.passwordEncoder = passwordEncoder;
//        this.jdbc= jdbc;
//
//
//    }

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
        //user.setRoleId(2L);
        System.out.println(user.getPassword());
        String userInsertSql =
                "insert into users " +
                        "(username, email, password, role_id)" +
                        " values (?,?,?,?)";

        jdbc.update(userInsertSql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoleId());

    }

    public void updateUser(User user) {
        String userUpdateSql =
                "update users set " +
                        "username = ?, " +
                        "email = ?, " +
                        "password = ?, " +
                        "role_id = ? " +
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
        int count = jdbc.queryForObject(sql, Integer.class, email);
        return count > 0;
    }


    public User getUserByUserName(String username) {
        String sql = "select * from users where username = ?";
        return jdbc.queryForObject(sql, userMapper, username);
    }

//    public void updateUser(User user) {
//        String sql = "update users set username = ?, email = ?, password = ? where id = ?";
//        jdbc.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getId());
//    }
// getting users not having a form this year


    public List<User> getUsersNotHavingFormThisYear() {
        String sql = "select * from users join 360forms on users.username =360forms.username where users.username not in (select username from 360forms where created_at = year(now()))";
        return jdbc.query(sql, userMapper);
    }


}
