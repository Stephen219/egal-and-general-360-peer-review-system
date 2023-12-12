package uk.cf.ac.LegalandGeneralTeam11.user;

import java.util.List;

public interface UserService {

    User getUserEmail(String username);

    List<User> getUsers();
    User getUser(Long id);
    void save(User user);
    void encodePassword(User user);

    boolean checkUserExists(String email);
}
