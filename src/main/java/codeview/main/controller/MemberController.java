package codeview.main.controller;

import codeview.main.entity.Member;
import codeview.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<Member> getUserInfo(Authentication authentication) {
        String email = authentication.getName();
        Optional<Member> member = memberService.findByEmail(email);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public ResponseEntity<Member> updateUserInfo(Authentication authentication, @RequestBody Member updatedMember) {
        String email = authentication.getName();
        Optional<Member> member = memberService.findByEmail(email);

        if (member.isPresent()) {
            Member currentMember = member.get();
            currentMember.setName(updatedMember.getName());
            currentMember.setProfile(updatedMember.getProfile());
            memberService.save(currentMember);
            return new ResponseEntity<>(currentMember, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<Member> member = memberService.findByEmail(email);

        if (member.isPresent()) {
            memberService.deleteByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
