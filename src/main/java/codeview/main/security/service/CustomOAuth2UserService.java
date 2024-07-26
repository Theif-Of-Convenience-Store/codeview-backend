package codeview.main.security.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            System.out.println("User info loaded: " + oAuth2User.getAttributes());

            // 사용자 정보를 처리하고 필요 시 새로운 사용자 생성 또는 업데이트
            String email = oAuth2User.getAttribute("email");
            if (email == null) {
                throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
            }

            // 예제: User user = userService.findOrCreateUser(oAuth2User);

            return oAuth2User;
        } catch (Exception e) {
            System.out.println("Error loading user: " + e.getMessage());
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }
}
