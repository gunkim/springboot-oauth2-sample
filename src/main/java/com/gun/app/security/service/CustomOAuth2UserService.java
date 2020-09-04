package com.gun.app.security.service;

import com.gun.app.domain.Member;
import com.gun.app.domain.MemberRepository;
import com.gun.app.security.dto.OAuthAttributes;
import com.gun.app.security.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * OAuth2 응답 처리를 위한 서비스
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final MemberRepository memberRepository;

    private final HttpSession httpSession;

    /**
     * Provider에게 유저 정보를 반환하고, 세션에 유저 정보를 저장
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();


        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //어떤 로그인 서비스인지 구분하는 코드, 구글, 네이버 등 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //primary key를 의미, 구글은 기본 지원
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdate(attributes);

        //세션에 사용자 정보를 저장하기 위한 dto 클래스
        httpSession.setAttribute("member", new SessionUser(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }


    /**
     * TODO: 이름에 해당하는 유저가 있으면 업데이트하고, 없을 경우 입력함.
     * email이 아니라 바뀔 수 있는 이름을 키로 잡은 이유는 카카오 같은 경우 이메일 없이도 가입이 가능하고,
     * 깃허브 같은 경우는 이메일을 반환하지 않아서, 예제 처리를 위해서 일단 공통으로 모두 무조건 반환하는 이름을 통해
     * 처리를 했음.
     * @param attributes
     * @return
     */
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByName(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }
}