package uk.cf.ac.LegalandGeneralTeam11.FormRequestTests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestController;
import uk.cf.ac.LegalandGeneralTeam11.FormRequest.FormRequestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormRequestControllerTests {

    @Mock
    private FormRequestService formRequestService;

    @InjectMocks
    private FormRequestController formRequestController;

    @Test
    void testAddFormRequest() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", "testPassword");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        doNothing().when(formRequestService).createFormRequest(any());
        ModelAndView modelAndView = formRequestController.addFormRequest(redirectAttributes);
        verify(formRequestService, times(1)).createFormRequest(any());
        assertEquals("redirect:/account", modelAndView.getViewName());
    }

}

