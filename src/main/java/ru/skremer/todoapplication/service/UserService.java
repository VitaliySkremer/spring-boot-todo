package ru.skremer.todoapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skremer.todoapplication.controller.common.dto.UserCreate;
import ru.skremer.todoapplication.entity.User;
import ru.skremer.todoapplication.entity.UserRole;
import ru.skremer.todoapplication.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User save(UserCreate userCreate) {
        String encodedPassword = passwordEncoder.encode(userCreate.getPassword());

        User user = User.builder()
                .name(userCreate.getName())
                .password(encodedPassword)
                .email(userCreate.getEmail())
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return user;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication is null");
        }

        String email = authentication.getName();

        return userRepository
                .findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
