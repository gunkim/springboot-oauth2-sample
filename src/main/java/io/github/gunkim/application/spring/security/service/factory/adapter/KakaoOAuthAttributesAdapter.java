package io.github.gunkim.application.spring.security.service.factory.adapter;

import io.github.gunkim.application.spring.security.service.dto.OAuthAttributes;
import io.github.gunkim.application.spring.security.service.factory.OAuthAttributesAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuthAttributesAdapter implements OAuthAttributesAdapter {
    private static final String REGISTRATION_ID = "kakao";

    @Override
    public OAuthAttributes toOAuthAttributes(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        properties.put("id", attributes.get("id"));

        return OAuthAttributes.builder()
                .name((String) properties.get("nickname"))
                .email((String) properties.get("email"))
                .picture((String) properties.get("profile_image"))
                .attributes(properties)
                .nameAttributeKey("nickname")
                .registrationId(REGISTRATION_ID)
                .build();
    }

    @Override
    public boolean supports(String registrationId) {
        return REGISTRATION_ID.equals(registrationId);
    }
}
