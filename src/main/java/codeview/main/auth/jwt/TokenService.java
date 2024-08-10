package codeview.main.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final Map<String, Token> tokenStore = new HashMap<>();

    public void saveOrUpdate(String username, String refreshToken, String accessToken) {
        Token token = new Token(username, refreshToken, accessToken);
        tokenStore.put(username, token);
    }

    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenStore.values().stream()
                .filter(token -> token.getAccessToken().equals(accessToken))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public void updateToken(String reissueAccessToken, Token token) {
        token.setAccessToken(reissueAccessToken);
    }
}
