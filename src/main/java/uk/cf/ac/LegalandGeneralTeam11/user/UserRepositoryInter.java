package uk.cf.ac.LegalandGeneralTeam11.user;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepositoryInter {


    List<User> getUsers();

    User getUser(Long id);

    User getUserEmail(String username);

    public void encodePassword(User user);

    void save(User user);


    boolean checkUserExists(String email);

    User getUserByUserName(String username);
}


