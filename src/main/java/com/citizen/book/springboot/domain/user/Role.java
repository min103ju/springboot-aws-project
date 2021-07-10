package com.citizen.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    // TODO: Notion에 기재 할 것.
    // 스프링 시큐리티는 권한 코드에 항상 ROLE_이 prefix로 있어야한다.
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
