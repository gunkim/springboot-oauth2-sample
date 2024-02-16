package io.github.gunkim.security.service.factory;

import io.github.gunkim.security.service.dto.OAuthAttributes;

import java.util.Map;

/**
 * OAuth2 로그인 후 반환되는 응답 결과를 우리의 서비스에 맞는 응답 결과로 변환하는 역할을 합니다.
 */
public interface OAuthAttributesAdapter {
    OAuthAttributes toOAuthAttributes(Map<String, Object> attributes);

    boolean supports(String registrationId);
}