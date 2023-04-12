package com.gun.application.spring.security.dto;

import com.gun.domain.Member;
import java.io.Serializable;
import lombok.Getter;

/**
 * 유저 정보를 세션에 등록하기 위한 직렬화 객체
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.name();
        this.email = member.email();
        this.picture = member.picture();
    }
}
