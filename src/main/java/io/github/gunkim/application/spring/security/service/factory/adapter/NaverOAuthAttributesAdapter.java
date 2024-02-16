package io.github.gunkim.application.spring.security.service.factory.adapter;

import io.github.gunkim.application.spring.security.service.dto.OAuthAttributes;
import io.github.gunkim.application.spring.security.service.factory.OAuthAttributesAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NaverOAuthAttributesAdapter implements OAuthAttributesAdapter {
    private static final String REGISTRATION_ID = "naver";

    @Override
    public OAuthAttributes toOAuthAttributes(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profileImage"))
                .attributes(response)
                .nameAttributeKey("name")
                .registrationId(REGISTRATION_ID)
                .build();
    }

    @Override
    public boolean supports(String registrationId) {
        return REGISTRATION_ID.equals(registrationId);
    }
}
