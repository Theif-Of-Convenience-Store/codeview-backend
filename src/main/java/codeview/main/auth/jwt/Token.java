package codeview.main.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private String username;
    private String refreshToken;
    private String accessToken;
}
