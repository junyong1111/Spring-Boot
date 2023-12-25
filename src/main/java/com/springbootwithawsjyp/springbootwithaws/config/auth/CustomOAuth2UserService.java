package com.springbootwithawsjyp.springbootwithaws.config.auth;

import com.springbootwithawsjyp.springbootwithaws.config.auth.dto.OAuthAttributes;
import com.springbootwithawsjyp.springbootwithaws.config.auth.dto.SessionUser;
import com.springbootwithawsjyp.springbootwithaws.domain.users.UserRepository;
import com.springbootwithawsjyp.springbootwithaws.domain.users.Users;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId();
        // ↑ 현재 로그인 진행 중인 서비스를 구분하는 코드
        // ↑ 구글만 사용하여 불필요하지만 이후 다른 소셜 로그인을 구분할 때 사용


        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        // ↑ OAuth2 로그인 시 키가 되는 필드값을 이야기한다.
        // ↑ 구글의 경우 기본적으로 코드("sub")를 지원하지만, 네이버 카카오 등은 지원하지 않는다.

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());
        // ↑ OAuth2User의 특성을 담을 클래스이며 다른 소셜 로그인도 이 클래스를 사용한다.

        Users user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributekey());
    }

    private Users saveOrUpdate(OAuthAttributes attributes){
        Users user = userRepository.findByEmail(attributes.getEmail())
                .map(
                        entity -> entity.update(
                                attributes.getName(),
                                attributes.getPicture())
                ).orElse(attributes.toEntity());

        return userRepository.save(user);
    }



}
