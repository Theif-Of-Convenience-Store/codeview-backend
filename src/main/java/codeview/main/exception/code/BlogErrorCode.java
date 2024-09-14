package codeview.main.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogErrorCode implements ErrorCode {
    BLOG_NOT_FOUND("Blog not found", "B100");

    private final String message;
    private final String status;
}
