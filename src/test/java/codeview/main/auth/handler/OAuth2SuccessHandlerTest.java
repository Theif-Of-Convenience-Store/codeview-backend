package codeview.main.auth.handler;

import codeview.main.auth.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        when(tokenProvider.generateAccessToken(authentication)).thenReturn("mockAccessToken");
        when(tokenProvider.generateRefreshToken(authentication, "mockAccessToken")).thenReturn("mockRefreshToken");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        // JSON 문자열을 파싱하여 비교
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = "{\"code\":200,\"result\":{\"accessToken\":\"mockAccessToken\",\"refreshToken\":\"mockRefreshToken\"}}";
        String actualJson = "{\"result\":{\"accessToken\":\"mockAccessToken\",\"refreshToken\":\"mockRefreshToken\"},\"code\":200}";

        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(actualJson));
    }


}
