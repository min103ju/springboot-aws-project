package com.citizen.book.springboot.config.auth;

import com.citizen.book.springboot.config.auth.dto.OAuthAttributes;
import com.citizen.book.springboot.config.auth.dto.SessionUser;
import com.citizen.book.springboot.domain.user.User;
import com.citizen.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
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

// TODO: Notion에 기재 할 것.
// 해당 클래스에서 구글 로그인 이후 가져온 사용자의 정보(email, name, picture 등)들을 기반으로
// 가입 및 정보 수정, 세션 저장 등의 기능을 지원한다.
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate
                = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 현재는 구글 서비스만 연동하므로 불필요하지만 이후 네이버, 카카오 등 다른 소셜 로그인 연동시에 
        // 어떤 서비스 로그인을 진행 중인지 필요하다
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName : OAuth2 로그인 진행 시 user의 키가 되는 필드값
        // Primary Key
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버, 카카오 등은 기본 지원하지 않는다.
        // 구글의 기본 코드는 sub이다.
        // 이후 네이버 로그인과 구글 로그인을 동시 지원할때 사용된다.
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuthUser의 attribute를 담을 클래스
        // 다른 소셜 로그인에서도 이 클래스를 이용
        OAuthAttributes attribues
                = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 구글사용자 정보가 업데이트 되었을때를 대비한 method
        User user = saveOrUpdate(attribues);

        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // User가 아닌 SessionUser를 사용하는 이유는
        // httpSession에 set하기 위해선 직렬화가 필요하다
        // Entity인 클래스를 직렬화하게 되면 관계가 되어있는 다른 엔티티에서 사이드 이펙트가 발생할 여지가 있다.(성능이슈, 부수 효과)
        // 때문에 직렬화 기능을 가진 SessionUser Dto를 추가하여 사용한 것.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attribues.getAttributes(),
                attribues.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
