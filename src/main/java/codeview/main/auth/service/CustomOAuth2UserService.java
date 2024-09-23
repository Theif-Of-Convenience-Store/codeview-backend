package codeview.main.auth.service;

import codeview.main.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserRepository userRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        return getOrSave(userRequest, oAuth2User);
    }

    public OAuth2User getOrSave(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = extractEmail(registrationId, attributes, oAuth2User.getName());

        if (!isValidEmail(email)) {
            logger.warn("유효하지 않은 이메일 형식: {}", email);
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }

        if (userRepository.existsByEmail(email)) {
            logger.warn("중복된 이메일: {}", email);
            throw new IllegalArgumentException("해당 이메일로 이미 가입된 사용자가 있습니다.");
        }

        return oAuth2User;
    }

    private String extractEmail(String registrationId, Map<String, Object> attributes, String userId) {
        String email = null;

        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
        } else if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
        } else if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");
            }
        }

        if (email == null || email.isEmpty()) {
            email = registrationId + "_" + userId + "@example.com";
            logger.warn("OAuth2 서비스에서 이메일을 제공하지 않음. 기본 이메일 사용: {}", email);
        } else {
            logger.info("OAuth2 서비스에서 이메일을 제공받음: {}", email);
        }

        return email;
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
