package codeview.main.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND("User not found", "U100"),
    UNAUTHORIZED_USER("an unauthorized user", "U200");

    private final String message;
    private final String status;
}
