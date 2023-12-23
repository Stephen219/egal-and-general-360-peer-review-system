package uk.cf.ac.LegalandGeneralTeam11.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.cf.ac.LegalandGeneralTeam11.user.UserRepo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepositoryInter usersRepository;
    public UserServiceImpl(UserRepositoryInter usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> getUsers() {
        return usersRepository.getUsers();
    }

    public User getUser(Long id) {
        return usersRepository.getUser(id);
    }

    public void save(User user) {
        usersRepository.save(user);
    }
    public User getUserEmail(String username) {
        return usersRepository.getUserEmail(username);
    }
    public void encodePassword(User user) {
        usersRepository.encodePassword(user);
    }

    public boolean checkUserExists(String email, String username) {
        return usersRepository.checkUserExists(email, username);
    }

    public User getUserByUserName(String username) {
        return usersRepository.getUserByUserName(username);
    }

    public void updateUser(User user) {
        usersRepository.updateUser(user);
    }
    public List<User> getUsersNotHavingFormThisYear() {
        return usersRepository.getUsersNotHavingFormThisYear();
    }


    /**
     * generateActivationToken
     * @param user
     */


    public String generateActivationToken(User user ,String type) {
        return usersRepository.generateActivationToken(user,type);}


    /**
     * checkTokenUsable method
     * returns boolean TRUE IF  the token is not expired and not used
     * @param token
     * @return
     */




    public boolean checkTokenUsable(String token) {
        return (!usersRepository.isTokenExpired(token) && !usersRepository.isTokenUsed(token));

    }


/**
     * getActivationToken method
     * @param token
     * @return
     */
    public TokenDto getActivationToken(String token) {
        return usersRepository.getActivationToken(token);}


    /**
     * validatePassword method   throws an exception if the password and confirm password are not same
     * throws another exception if the password  not a regex
     * @param password
     * @param confirmPassword
     * @return
     */


    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$";



    public boolean validatePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        }
        if (!password.matches(PASSWORD_REGEX)) {
            throw new RuntimeException("Password must be at least 8 characters long and contain at least one number and one letter.");
        }
        return true;
    }


    /**
     * setTokenUsed method
     * @param token
     */

    public void setTokenUsed(String token) {
        usersRepository.setTokenUsed(token);}



    public User getUserByEmail(String email) {
        return usersRepository.getUserByEmail(email);
    }


    public boolean validateChangePassword(String oldPassword, String email, String newPassword){
        return usersRepository.validateOldPassword(oldPassword,email) && !usersRepository.CheckIfNewPasswordIsSameAsOldPassword(newPassword,email);
    }



}
