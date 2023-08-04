package io.github.gunkim.application.spring.security;

import io.github.gunkim.application.spring.security.service.CustomOAuth2UserService;
import io.github.gunkim.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/", "/css/**", "/img/**", "/js/**", "/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/user").hasRole(Role.USER.name())
                .anyRequest().authenticated().and()
                .logout().logoutSuccessUrl("/").and()
                .oauth2Login().userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}
