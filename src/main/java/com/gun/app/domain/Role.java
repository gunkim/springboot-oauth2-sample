package com.gun.app.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 권한 enum
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}