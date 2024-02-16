package io.github.gunkim.application.spring.security.service.factory.adapter;

import io.github.gunkim.application.spring.security.service.dto.OAuthAttributes;
import io.github.gunkim.application.spring.security.service.factory.OAuthAttributesAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GithubOAuthAttributesAdapter implements OAuthAttributesAdapter {
    private static final String REGISTRATION_ID = "github";

    @Override
    public OAuthAttributes toOAuthAttributes(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey("login")
                .registrationId(REGISTRATION_ID)
                .build();
    }

    @Override
    public boolean supports(String registrationId) {
        return REGISTRATION_ID.equals(registrationId);
    }
}
