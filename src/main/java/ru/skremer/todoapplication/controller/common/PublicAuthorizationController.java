package ru.skremer.todoapplication.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skremer.todoapplication.controller.common.dto.UserCreate;
import ru.skremer.todoapplication.entity.User;
import ru.skremer.todoapplication.entity.UserRole;
import ru.skremer.todoapplication.service.UserService;

import java.util.Collections;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class PublicAuthorizationController {
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {

        if (error != null) {
            model.addAttribute("isAuthenticationFailed", true);
        }

        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration")
    public String createUserAccount(UserCreate userCreate,
                                    HttpServletRequest httpRequest,
                                    HttpServletResponse httpResponse
    ) {
        User user = userService.save(userCreate);

        forceAutoLogin(userCreate.getEmail(), user.getPassword(), httpRequest, httpResponse);

        return "redirect:/account";
    }

    private void forceAutoLogin(String email, String password, HttpServletRequest httpRequest,
                                HttpServletResponse httpResponse) {
        Set<SimpleGrantedAuthority> roles = Collections.singleton(UserRole.USER.toAuthority());
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(email, password, roles);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);
    }
}
