package javacode_spring_security_oauth.controller;

import javacode_spring_security_oauth.service.SocialAppService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StartController {

    private final SocialAppService socialAppService;

    @GetMapping("/")
    public String home() {
        return "Hello";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
