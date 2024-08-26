package codeview.main.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void saveOrUpdate(String username, String refreshToken, String accessToken) {
        Optional<Token> existingToken = tokenRepository.findByUsername(username);
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setRefreshToken(refreshToken);
            token.setAccessToken(accessToken);
            tokenRepository.save(token);
        } else {
            Token newToken = new Token(null, username, refreshToken, accessToken);
            tokenRepository.save(newToken);
        }
    }

    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    @Transactional
    public void updateToken(String reissueAccessToken, Token token) {
        token.setAccessToken(reissueAccessToken);
        tokenRepository.save(token);
    }
}
