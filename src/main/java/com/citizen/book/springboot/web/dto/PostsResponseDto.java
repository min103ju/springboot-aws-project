package com.citizen.book.springboot.web.dto;

import com.citizen.book.springboot.domain.posts.Posts;
import lombok.Getter;

// TODO: Notion에 기재 할 것.
// 화면에 필요한 값을 지닌 Dto
// Entity의 모든 필드를 가진 생성자가 필요하진 않다.
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
