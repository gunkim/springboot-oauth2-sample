package io.github.gunkim.application.spring.security.service.dto;

import io.github.gunkim.domain.Member;
import io.github.gunkim.domain.Role;
import io.github.gunkim.domain.Social;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuthAttributes(
        Map<String, Object> attributes,
        String nameAttributeKey,
        String name,
        String email,
        String picture,
        String registrationId
) {
    public Member toEntity() {
        return Member.builder().name(name).email(email).picture(picture).role(Role.USER)
                .social(Social.valueOf(registrationId.toUpperCase())).build();
    }
}
