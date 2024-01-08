package uk.cf.ac.LegalandGeneralTeam11.user;

import java.util.List;

public interface UserService {

    User getUserEmail(String username);

    List<User> getUsers();
    User getUser(Long id);
    void save(User user);
    void encodePassword(User user);

    boolean checkUserExists(String email, String username);

    User getUserByUserName(String username);

    void updateUser(User user);
    public List<User> getUsersNotHavingFormThisYear();

    public String generateActivationToken(User user ,String type);


    public boolean checkTokenUsable(String token);
    public TokenDto getActivationToken(String token);

    public boolean validatePassword(String password, String confirmPassword);


    public void setTokenUsed(String token);

    public User getUserByEmail(String email);

    public boolean validateChangePassword(String oldPassword, String email, String newPassword);

}
