package com.gun.app.security.dto;

import com.gun.app.domain.Member;
import lombok.Getter;

import java.io.Serializable;

/**
 * 유저 정보를 세션에 등록하기 위한 직렬화 객체
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}