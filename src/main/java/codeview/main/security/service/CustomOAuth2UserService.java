package codeview.main.security.service;

import codeview.main.entity.User;
import codeview.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String socialId = "", name = "";
        switch (registrationId) {
            case "github":
                socialId = attributes.get("id").toString();
                name = attributes.get("name").toString();
        }
        Optional<User> findUser = userRepository.findBySocialId(socialId);
        if (findUser.isEmpty()) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setSocialId(socialId);
            userRepository.save(newUser);
        }


        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName
        );

//        try {
//            System.out.println("User info loaded: " + oAuth2User.getAttributes());
//
//            // 사용자 정보를 처리하고 필요 시 새로운 사용자 생성 또는 업데이트
//            String email = oAuth2User.getAttribute("email");
//            System.out.println("email : " + email);
//            if (email == null) {
//                System.out.println("!!!!!!!!!?");
//                throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
//            }
//
//            // 예제: User user = userService.findOrCreateUser(oAuth2User);
//            System.out.println("!!!!!!!!!");
//            return oAuth2User;
//        } catch (Exception e) {
//            System.out.println("!!!!!!!!!??");
//            System.out.println("Error loading user: " + e.getMessage());
//            throw new OAuth2AuthenticationException(e.getMessage());
//        }
    }
}
