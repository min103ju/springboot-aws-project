package com.citizen.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// TODO: Notion에 기재 할 것.
// 해당 Annotation이 생성될 수 있는 위치를 지정
// PARAMETER로 지정하여 메소드의 parameter로 선언된 객체에만 사용 가능
// Annotation의 위치에 대해 notion에 기재
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
