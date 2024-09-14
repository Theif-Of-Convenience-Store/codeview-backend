package codeview.main.auth.handler;

import codeview.main.auth.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
@SpringBootTest
public class OAuth2SuccessHandlerTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(oAuth2SuccessHandler).build();
    }

    @Test
    public void testOnAuthenticationSuccess_withRedirect() throws Exception {
        // 가짜 토큰 생성
        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";
        String redirectUrl = "http://localhost:3000";

        // TokenProvider 모의 설정
        when(tokenProvider.generateAccessToken(authentication)).thenReturn(accessToken);
        when(tokenProvider.generateRefreshToken(authentication, accessToken)).thenReturn(refreshToken);

        // OAuth2SuccessHandler가 요청을 처리하는지 테스트
        mockMvc.perform(get("/oauth2/authorization/kakao")
                        .param("redirect", redirectUrl))
                .andExpect(redirectedUrl(String.format("%s/auth/token?accessToken=%s&refreshToken=%s",
                        redirectUrl, accessToken, refreshToken)));
    }

    @Test

    public void testOnAuthenticationSuccess_withoutRedirect() throws Exception {
        // 가짜 토큰 생성
        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";
        String defaultRedirectUrl = "http://localhost:3000";

        // TokenProvider 모의 설정
        when(tokenProvider.generateAccessToken(authentication)).thenReturn(accessToken);
        when(tokenProvider.generateRefreshToken(authentication, accessToken)).thenReturn(refreshToken);

        // 리다이렉트 파라미터가 없을 때 기본 URL로 리디렉트 되는지 확인
        mockMvc.perform(get("/oauth2/authorization/kakao"))
                .andExpect(redirectedUrl(String.format("%s/auth/token?accessToken=%s&refreshToken=%s",
                        defaultRedirectUrl, accessToken, refreshToken)));
    }
}
