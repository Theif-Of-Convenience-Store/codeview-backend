package codeview.main.auth.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

public class TokenException extends RuntimeException {
    private final OAuth2Error error;

    public TokenException(String message) {
        super(message);
        this.error = new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, message, null);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
        this.error = new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, message, null);
    }

    public OAuth2Error getError() {
        return error;
    }

    public HttpStatus getErrorCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
