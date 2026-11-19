package ru.skremer.todoapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skremer.todoapplication.entity.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/", "/login", "/registration", "/error",
                                "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                        .requestMatchers("/account/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name())
                        .requestMatchers("/management/**").hasRole(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
