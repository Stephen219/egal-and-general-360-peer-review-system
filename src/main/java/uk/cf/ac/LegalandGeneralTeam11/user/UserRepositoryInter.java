package uk.cf.ac.LegalandGeneralTeam11.user;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepositoryInter {


    List<User> getUsers();

    User getUser(Long id);

    User getUserEmail(String username);

    public void encodePassword(User user);

    void save(User user);


    boolean checkUserExists(String email, String username);

    User getUserByUserName(String username);


    void updateUser(User user);

    public List<User> getUsersNotHavingFormThisYear();

    public String generateActivationToken(User user, String type);
    public boolean isTokenUsed(String token);
    public boolean isTokenExpired(String token);


    public TokenDto getActivationToken(String token);

    public void setTokenUsed(String token);

    public User getUserByEmail(String email);
    public boolean validateOldPassword(String oldPassword, String email);
    public boolean CheckIfNewPasswordIsSameAsOldPassword(String newPassword, String email);







}


