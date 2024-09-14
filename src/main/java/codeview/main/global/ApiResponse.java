package codeview.main.global;


import codeview.main.exception.code.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> createResponse(String status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }
    public static <T> ApiResponse<T> createResponse(String status, String message) {
        return ApiResponse.createResponse(status, message, null);
    }


    public static <Void >ApiResponse<Void> createErrorResponse(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getStatus(), errorCode.getMessage(), null);
    }
    public static <T> ApiResponse<T> createErrorResponse(ErrorCode errorCode, T data) {
        return new ApiResponse<>(errorCode.getStatus(), errorCode.getMessage(), data);
    }
}