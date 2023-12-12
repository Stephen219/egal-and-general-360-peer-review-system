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

    public boolean checkUserExists(String email) {
        return usersRepository.checkUserExists(email);
    }
}
