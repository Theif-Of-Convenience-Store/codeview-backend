package codeview.main.exception.code;

import codeview.main.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    NOT_FOUND_RESOURCE("Not found resource", "G100"),
    INTERNAL_SERVER_ERROR("Internal server error", "G999");

    private final String message;
    private final String status;
}
