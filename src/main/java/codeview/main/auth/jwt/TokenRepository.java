package codeview.main.auth.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByUsername(String username);
}
