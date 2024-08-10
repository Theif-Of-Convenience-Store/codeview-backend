package codeview.main.auth.dto;

import codeview.main.entity.Member;
import codeview.main.util.KeyGenerator;
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
            default:
                throw new IllegalArgumentException("Illegal registration ID: " + registrationId);
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
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .profile(profile)
                .memberKey(KeyGenerator.generateKey())
                .role(Member.Role.ROLE_USER)
                .build();
    }
}
