package codeview.main.controller;

import codeview.main.dto.user.UserInfoResponseDto;
import codeview.main.entity.User;
import codeview.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(new UserInfoResponseDto(user), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserInfoResponseDto> updateUserInfo(Authentication authentication, @RequestBody UserInfoResponseDto userInfoDto) {
        String email = authentication.getName();
        User updatedUser = userService.updateUserInfo(email, userInfoDto);
        return new ResponseEntity<>(new UserInfoResponseDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
