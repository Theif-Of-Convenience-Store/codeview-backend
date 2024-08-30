package codeview.main.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {

    @GetMapping("/login/oauth2/code/google")
    public String googleCallback(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        return "home";
    }

    @GetMapping("/login/oauth2/code/github")
    public String githubCallback(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        return "home";
    }

//    @GetMapping("/login/oauth2/code/kakao")
//    public String kakaoCallback(@AuthenticationPrincipal OAuth2User principal, Model model) {
//        model.addAttribute("name", principal.getAttribute("nickname"));
//        return "home";
//    }
}

