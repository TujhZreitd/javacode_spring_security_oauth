package javacode_spring_security_oauth.controller;

import javacode_spring_security_oauth.service.SocialAppService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StartController {

  /*  private final UserService userService;*/

    private final SocialAppService socialAppService;

    @GetMapping("/")
    public String home() {
        return "Hello";
    }


   /* @GetMapping("/secured")
    public String addUser() {
        return userService.addUser();
    }*/

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("login", principal.getAttribute("login"));
        model.addAttribute("id", principal.getAttribute("id"));
        model.addAttribute("email", principal.getAttribute("email"));
        return "user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
