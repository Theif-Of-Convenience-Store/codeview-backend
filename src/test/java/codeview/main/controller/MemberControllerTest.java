package codeview.main.controller;

import codeview.main.entity.Member;
import codeview.main.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private MemberController memberController;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = Member.builder()
                .email("test@example.com")
                .name("SoEun")
                .profile("Profile Image")
                .build();
    }

    @Test
    void testGetUserInfo_Success() {
        when(authentication.getName()).thenReturn(member.getEmail());
        when(memberService.findByEmail(anyString())).thenReturn(Optional.of(member));

        ResponseEntity<Member> response = memberController.getUserInfo(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(member, response.getBody());
    }

    @Test
    void testGetUserInfo_NotFound() {
        when(authentication.getName()).thenReturn(member.getEmail());
        when(memberService.findByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Member> response = memberController.getUserInfo(authentication);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateUserInfo_Success() {
        when(authentication.getName()).thenReturn(member.getEmail());
        when(memberService.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(memberService.save(any(Member.class))).thenReturn(member);

        Member updatedMember = Member.builder().name("Jane Doe").profile("Updated Profile").build();
        ResponseEntity<Member> response = memberController.updateUserInfo(authentication, updatedMember);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane Doe", response.getBody().getName());
        assertEquals("Updated Profile", response.getBody().getProfile());
    }

    @Test
    void testUpdateUserInfo_NotFound() {
        when(authentication.getName()).thenReturn(member.getEmail());
        when(memberService.findByEmail(anyString())).thenReturn(Optional.empty());

        Member updatedMember = Member.builder().name("Jane Doe").build();
        ResponseEntity<Member> response = memberController.updateUserInfo(authentication, updatedMember);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser_Success() {
        when(authentication.getName()).thenReturn(member.getEmail());
        when(memberService.findByEmail(anyString())).thenReturn(Optional.of(member));

        ResponseEntity<Void> response = memberController.deleteUser(authentication);

        verify(memberService, times(1)).deleteByEmail(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteUser_NotFound() {
        when(authentication.getName()).thenReturn(member.getEmail());
        when(memberService.findByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Void> response = memberController.deleteUser(authentication);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
