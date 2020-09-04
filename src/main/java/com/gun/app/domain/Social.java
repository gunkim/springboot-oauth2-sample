package com.gun.app.domain;

import lombok.RequiredArgsConstructor;

/**
 * 소셜 로그인 관리 enum
 */
@RequiredArgsConstructor
public enum Social {
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글"),
    FACEBOOK("페이스북"),
    GITHUB("깃허브");

    private final String title;
}