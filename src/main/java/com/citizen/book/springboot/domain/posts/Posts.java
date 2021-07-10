package com.citizen.book.springboot.domain.posts;

import com.citizen.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Annotation의 주요 우선순위 대로 Class와 가깝게
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // length = varchar의 길이를 지정
    @Column(length = 500, nullable = false)
    private String title;

    // columnDefinition = data type을 지정
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    @Builder
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
