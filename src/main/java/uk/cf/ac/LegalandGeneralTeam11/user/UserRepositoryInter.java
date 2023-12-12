package uk.cf.ac.LegalandGeneralTeam11.user;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepositoryInter {




    List<User> getUsers();
    User getUser(Long id);
    User getUserEmail(String email);
    public void encodePassword(User user);
    void save(User user);


    boolean checkUserExists(String email);
    // Implement methods from JpaRepository
}
