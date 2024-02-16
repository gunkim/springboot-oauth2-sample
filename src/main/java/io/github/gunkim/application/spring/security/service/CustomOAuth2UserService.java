package io.github.gunkim.application.spring.security.service;

import io.github.gunkim.application.spring.security.service.dto.OAuthAttributes;
import io.github.gunkim.application.spring.security.service.factory.OAuthAttributesAdapterFactory;
import io.github.gunkim.domain.Member;
import io.github.gunkim.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final OAuthAttributesAdapterFactory oAuthAttributesAdapterFactory;
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        var registrationId = userRequest.getClientRegistration().getRegistrationId();
        var attributes = oAuthAttributes(registrationId, oAuth2User);
        var member = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(member.roleKey())),
                attributes.attributes(),
                attributes.nameAttributeKey()
        );
    }

    private OAuthAttributes oAuthAttributes(String registrationId, OAuth2User oAuth2User) {
        return oAuthAttributesAdapterFactory.factory(registrationId)
                .toOAuthAttributes(oAuth2User.getAttributes());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByName(attributes.email())
                .map(entity -> entity.update(attributes.name(), attributes.picture()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
