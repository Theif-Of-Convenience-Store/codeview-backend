package codeview.main.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        super.onAuthenticationSuccess(request, response, authentication);
        String accessToken = jwtTokenProvider.createToken(authentication);
        String redirectUrl = "/home";
        System.out.println(accessToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//        try {
//            String token = jwtTokenProvider.createToken(authentication);
//            response.addHeader("Authorization", "Bearer " + token);
//            System.out.println("OAuth2 Login Successful. Token: " + token);
//
//            if (response.isCommitted()) {
//                response.sendRedirect("/home");
//                System.out.println("Redirecting to /home");
//            } else {
//                System.out.println("Response already committed.");
//            }
//        } catch (Exception e) {
//            System.out.println("Success handler error: " + e.getMessage());
//            response.sendRedirect("/login?error=true");
//        }
    }
}
