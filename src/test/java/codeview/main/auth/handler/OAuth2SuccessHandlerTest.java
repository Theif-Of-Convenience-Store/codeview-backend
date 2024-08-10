package codeview.main.auth.handler;

import codeview.main.auth.jwt.TokenProvider;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OAuth2SuccessHandlerTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOnAuthenticationSuccess() throws IOException, ServletException {
        when(tokenProvider.generateAccessToken(authentication)).thenReturn("access-token");

        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verify(tokenProvider, times(1)).generateAccessToken(authentication);
        verify(tokenProvider, times(1)).generateRefreshToken(authentication, "access-token");
        verify(response, times(1)).sendRedirect(anyString());
    }
}
