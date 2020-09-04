package com.gun.app.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 관리 Repository 클래스
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}