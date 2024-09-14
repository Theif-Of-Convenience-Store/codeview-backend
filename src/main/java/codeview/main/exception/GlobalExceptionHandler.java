package codeview.main.exception;

import codeview.main.global.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public <T> ApiResponse<T> handleBusinessException(BusinessException businessException) {
        return ApiResponse.createErrorResponse(businessException.getErrorCode());
    }
}
