package uk.cf.ac.LegalandGeneralTeam11.AddUserTesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserRepositoryInter;
import uk.cf.ac.LegalandGeneralTeam11.user.UserServiceImpl;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepositoryInter userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUsers() {
        List<User> mockUserList = Collections.singletonList(new User(1L, "username", "email", "password", 2L));
        when(userRepository.getUsers()).thenReturn(mockUserList);

        List<User> result = userService.getUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockUserList, result);
    }

    @Test
    void testGetUser() {
        Long userId = 1L;
        User mockUser = new User(userId, "username", "email", "password", 2L);
        when(userRepository.getUser(userId)).thenReturn(mockUser);

        User result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    void testSaveUser() {
        User newUser = new User(null, "newuser", "newemail@example.com", "password", 2L);
        doNothing().when(userRepository).save(newUser);

        userService.save(newUser);

        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User(1L, "existinguser", "existingemail@example.com", "password", 2L);
        doNothing().when(userRepository).updateUser(existingUser);

        userService.updateUser(existingUser);

        verify(userRepository, times(1)).updateUser(existingUser);
    }
    @Test
    void testGetUserEmail() {
        String email = "user@example.com";
        User mockUser = new User(1L, "username", "user@example.com", "password", 2L);
        when(userRepository.getUserEmail(email)).thenReturn(mockUser);

        User result = userService.getUserEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testEncodePassword() {
        User user = new User(1L, "username", "user@example.com", "plainPassword", 2L);
        doNothing().when(userRepository).encodePassword(user);

        userService.encodePassword(user);

        verify(userRepository, times(1)).encodePassword(user);
    }
}
