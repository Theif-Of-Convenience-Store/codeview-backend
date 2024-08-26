package codeview.main.service;

import codeview.main.entity.Member;
import codeview.main.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public void deleteByEmail(String email) {
        memberRepository.deleteByEmail(email);
    }
}
