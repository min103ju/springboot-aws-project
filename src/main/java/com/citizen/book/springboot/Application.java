package com.citizen.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Notion에 기재 할 것.
// @EnableJpaAuditing 제거
// 스트링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
// @SpringBootApplication이 있는 위치부터 설정을 읽어간다.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run 내장 Was를 실행
        SpringApplication.run(Application.class, args);
    }
}
