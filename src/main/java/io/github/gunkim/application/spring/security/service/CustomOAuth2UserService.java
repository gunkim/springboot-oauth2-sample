package io.github.gunkim.application.spring.security.service;

import io.github.gunkim.application.spring.security.dto.OAuthAttributes;
import io.github.gunkim.application.spring.security.dto.SessionUser;
import io.github.gunkim.domain.Member;
import io.github.gunkim.domain.MemberRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final OAuth2UserService oAuth2UserService;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
            .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
            oAuth2User.getAttributes());
        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute("member", new SessionUser(member));

        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority(member.roleKey())),
            attributes.attributes(),
            attributes.nameAttributeKey()
        );
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByName(attributes.email())
            .map(entity -> entity.update(attributes.name(), attributes.picture()))
            .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
