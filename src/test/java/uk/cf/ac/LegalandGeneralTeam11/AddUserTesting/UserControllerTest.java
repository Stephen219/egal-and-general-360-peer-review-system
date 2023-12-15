package uk.cf.ac.LegalandGeneralTeam11.AddUserTesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import uk.cf.ac.LegalandGeneralTeam11.user.User;
import uk.cf.ac.LegalandGeneralTeam11.user.UserController;
import uk.cf.ac.LegalandGeneralTeam11.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAddUserForm() {
        ModelAndView modelAndView = userController.getAddUserForm();
        assertEquals("add_user_form", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("user"));
    }

    @Test
    void testManageEmployees() {
        List<User> userList = new ArrayList<>();
        when(userService.getUsers()).thenReturn(userList);

        ModelAndView modelAndView = userController.manageEmployees();
        assertEquals("account/manage_employees", modelAndView.getViewName());
        assertSame(userList, modelAndView.getModel().get("users"));
    }

    @Test
    void testGetUserFormWithValidData() {
        User user = new User();
        userController.getUserForm(user);

        verify(userService, times(1)).save(user);
    }


    @Test
    void testGetUserForm() {
        User user = new User();
        ModelAndView modelAndView = userController.getUserForm(user);
        assertEquals("redirect:/", modelAndView.getViewName());
        verify(userService, times(1)).save(user);
    }


}
