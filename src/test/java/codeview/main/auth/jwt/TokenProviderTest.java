package codeview.main.auth.jwt;

import codeview.main.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenProviderTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenProvider.setKey("blueisme256bitsecretkey123456789012");
        tokenProvider.setSecretKey();
    }

    @Test
    void testValidateToken_ValidToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.getAuthorities()).thenAnswer(invocation -> Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));


        String token = tokenProvider.generateAccessToken(authentication);
        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        // Expired token generation
        String expiredToken = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(tokenProvider.getSecretKey(), io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();

        assertFalse(tokenProvider.validateToken(expiredToken));
    }

    @Test
    void testGetAuthentication_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.getAuthorities()).thenAnswer(invocation -> Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));



        String token = tokenProvider.generateAccessToken(authentication);

        Authentication result = tokenProvider.getAuthentication(token);
        Member principal = (Member) result.getPrincipal();

        assertEquals("test@example.com", principal.getEmail());
        assertEquals("ROLE_USER", principal.getRole().name());
    }
}
