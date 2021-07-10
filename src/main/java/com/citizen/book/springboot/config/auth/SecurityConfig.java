package com.citizen.book.springboot.config.auth;

import com.citizen.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// TODO: Notion에 기재 할 것.

@RequiredArgsConstructor
// Spring Seucurity 설정들을 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    // URL별 권한 관리를 설정하는 옵션의 시작점
                    .authorizeRequests()
                        .antMatchers("/", "/css/**", "/image/**", "/js/**", "/h2-console/**")
                        .permitAll()
                        // 권한 관리 대상을 지정하는 옵션
                        // URL, HTTP 메소드별로 관리가 가능
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        // 설정된 이외 나머지 URL들을 나타낸다.
                        .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    // OAuth2 로그인 기능에 대한 설정 진입점
                    .oauth2Login()
                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올때의 설정들을 담당
                        .userInfoEndpoint()
                        // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                        // 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자
                        // 하는 기능을 명시할 수 있다.
                        .userService(customOAuth2UserService);
    }
}
