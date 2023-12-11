@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        // Implement logic to add a user
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // Implement logic to update a user
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        // Implement logic to delete a user
        userRepository.deleteById(userId);
    }
}
