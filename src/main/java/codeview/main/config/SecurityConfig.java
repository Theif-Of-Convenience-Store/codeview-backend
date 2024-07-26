package codeview.main.config;

import codeview.main.security.service.CustomOAuth2UserService;
import codeview.main.security.service.CustomUserDetailsService;
import codeview.main.security.service.JwtAuthenticationFilter;
import codeview.main.security.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/home", "/login", "/oauth2/**", "/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            try {
                                String token = jwtTokenProvider.createToken(authentication);
                                response.addHeader("Authorization", "Bearer " + token);
                                System.out.println("OAuth2 Login Successful. Token: " + token);

                                if (!response.isCommitted()) {
                                    response.sendRedirect("/home");
                                    System.out.println("Redirecting to /home");
                                } else {
                                    System.out.println("Response already committed.");
                                }
                            } catch (Exception e) {
                                System.out.println("Success handler error: " + e.getMessage());
                                response.sendRedirect("/login?error=true");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            System.out.println("OAuth2 Login Failed. Exception: " + exception.getMessage());
                            response.sendRedirect("/login?error=true");
                        })
                );

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

