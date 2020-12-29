# 스프링 시큐리티와 OAuth2를 구현해보는 예제 프로젝트
![image](https://user-images.githubusercontent.com/45007556/91962961-31f83600-ed48-11ea-8d49-032bdd70002d.png)

# OAuth2 세팅 방법
> 각각 클라이언트 ID 및 암호키 발급 방법은 [여기](https://gunlog.dev/Spring-Security-OAuth2/)

## src/main/resources/applicatn-oauth.yml
```xml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: 구글 클라이언트 ID
            client-secret: 구글 암호키
            scope:
              - profile
              - email
          naver:
            client-id: 네이버 클라이언트 ID
            client-secret: 네이버 암호키
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
            authorization-grant-type: authorization_code
            scope:
              - name
              - email
              - profile_image
            client-name: Naver
          facebook:
            client-id: 페이스북 클라이언트 ID
            client-secret: 페이스북 암호키
            scope:
              - email
              - public_profile
          kakao:
            authorization-grant-type: authorization_code
            client-id: 카카오 클라이언트 ID
            client-secret: 카카오 암호키
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
            scope:
              - profile
              - email
            client-authentication-method: POST
            client-name: Kakao
          github:
            client-id: 깃허브 클라이언트 ID
            client-secret: 깃허브 암호키
        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
```
