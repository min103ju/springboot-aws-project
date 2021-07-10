package com.citizen.book.springboot;

import com.citizen.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// TODO: Notion에 기재 할 것.

// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행 시킴
// SpringRunner라는 스프링 실행자를 사용
// 즉! 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@RunWith(SpringRunner.class)

// Web 테스트에 집중할 수 있는 Annotation
// @Controller, @ControllerAdvice 등의 Bean을 사용가능
// @Service, @Component, @Repository는 사용 불가
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                // mvc.perform()의 결과를 검증 - HTTP Header의 상태 검증
                .andExpect(status().isOk())
                // Response의 body 검증
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount))
        )
                .andExpect(status().isOk())
                // jsonPath : Json 응답값을 필드별로 검증할 수 있는 메소드
                // &를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));


    }
}
