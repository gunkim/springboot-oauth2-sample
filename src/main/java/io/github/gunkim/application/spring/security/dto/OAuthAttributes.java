package io.github.gunkim.application.spring.security.dto;

import io.github.gunkim.domain.Member;
import io.github.gunkim.domain.Role;
import io.github.gunkim.domain.Social;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
@Accessors(fluent = true)
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String registrationId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String registrationId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.registrationId = registrationId;
    }

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "naver" -> ofNaver(registrationId, attributes);
            case "kakao" -> ofKakao(registrationId, attributes);
            case "github" -> ofGithub(registrationId, attributes);
            case "facebook" -> ofFacebook(registrationId, attributes);
            case "google" -> ofGoogle(registrationId, attributes);
            default -> throw new IllegalArgumentException("해당 로그인을 찾을 수 없습니다.");
        };
    }

    private static OAuthAttributes ofFacebook(String registrationId, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey("name")
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofGithub(String registrationId, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey("login")
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofKakao(String registrationId, Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        properties.put("id", attributes.get("id"));
        return OAuthAttributes.builder()
                .name((String) properties.get("nickname"))
                .email((String) properties.get("email"))
                .picture((String) properties.get("profile_image"))
                .attributes(properties)
                .nameAttributeKey("nickname")
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofGoogle(String registrationId, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey("name")
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofNaver(String registrationId, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profileImage"))
                .attributes(response)
                .nameAttributeKey("name")
                .registrationId(registrationId)
                .build();
    }

    public Member toEntity() {
        return Member.builder().name(name).email(email).picture(picture).role(Role.USER)
                .social(Social.valueOf(registrationId.toUpperCase())).build();
    }
}
