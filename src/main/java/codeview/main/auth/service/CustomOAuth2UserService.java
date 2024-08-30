package codeview.main.auth.service;

import codeview.main.auth.dto.model.PrincipalDetails;
import codeview.main.auth.dto.OAuth2UserInfo;
import codeview.main.entity.Member;
import codeview.main.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> oAuth2UserAttributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);

        // 이메일이 없는 경우 처리
        Optional<Member> memberOptional = Optional.empty();
        if (oAuth2UserInfo.getEmail() != null) {
            memberOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        }

        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
        } else {
            // 이메일이 없거나 기존 회원이 아닌 경우 새로 저장
            member = getOrSave(oAuth2UserInfo);
        }

        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }

    private Member getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        Member member = new Member();
        member.setName(oAuth2UserInfo.getName());
        member.setProfile(oAuth2UserInfo.getProfile());

        if (oAuth2UserInfo.getEmail() != null) {
            member.setEmail(oAuth2UserInfo.getEmail());
        } else {
            // 이메일이 없는 경우 다른 방법으로 식별자를 생성 (예: OAuth 제공자의 고유 ID 사용)
            member.setEmail(oAuth2UserInfo.getName() + "@example.com");
        }

        member.setRole(Member.Role.ROLE_USER);

        return memberRepository.save(member);
    }
}
