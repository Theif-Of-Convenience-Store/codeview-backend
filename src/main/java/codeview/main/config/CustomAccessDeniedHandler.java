package codeview.main.config;

import jakarta.servlet.ServletException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
    }

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

    }
}
