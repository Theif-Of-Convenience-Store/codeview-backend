package codeview.main.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

//@Getter
//public enum ErrorCode {
//    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
//    BLOG_NOT_FOUND(HttpStatus.NOT_FOUND, "Blog not found"),
//    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Board not found");
//
//    private final HttpStatus httpStatus;
//    private final String message;
//
//    ErrorCode(HttpStatus httpStatus, String message) {
//        this.httpStatus = httpStatus;
//        this.message = message;
//    }
//}
public interface ErrorCode {
    String getMessage();
    String getStatus();
}