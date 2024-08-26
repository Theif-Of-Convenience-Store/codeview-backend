package codeview.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/login/success")
    public String loginSuccess() {
        return "Login Successful!";
    }

    @GetMapping("/login/failure")
    public String loginFailure() {
        return "Login Failed!";
    }
}
