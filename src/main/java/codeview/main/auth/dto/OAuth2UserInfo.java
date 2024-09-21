package codeview.main.auth.dto;

import codeview.main.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class OAuth2UserInfo {
    private String name;
    private String email;
    private String profile;

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google":
                return ofGoogle(attributes);
            case "kakao":
                return ofKakao(attributes);
            case "github":
                return ofGithub(attributes);
            default:
                throw new IllegalArgumentException("Unsupported registration ID: " + registrationId);
        }
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    private static OAuth2UserInfo ofGithub(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("avatar_url"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email != null ? email : name + "@kakao.com")
                .logo(profile)
                .build();
    }
}
