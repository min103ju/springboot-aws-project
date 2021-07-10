package com.citizen.book.springboot.service;

import com.citizen.book.springboot.domain.posts.Posts;
import com.citizen.book.springboot.domain.posts.PostsRepository;
import com.citizen.book.springboot.web.dto.PostsListResponseDto;
import com.citizen.book.springboot.web.dto.PostsResponseDto;
import com.citizen.book.springboot.web.dto.PostsSaveRequestDto;
import com.citizen.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // TODO: Notion에 기재 할 것.

        // PersistContenxt 덕분에 Repository에 접근하지 않아도 update가 된다.
        // JPA의 핵심 내용은 Entity가 영속성 Context에 포함되냐 아니냐
        // JPA의 EntityManager가 활성화된 상태로 Transaction안에서 Database에서 데이터를 가지고 오면 이 데이터는
        // PersistContext가 유지된 상태이다.
        // 이 상태에서 해당 Data의 값이 변하면 Transaction이 끝나는 시점에 PersistContext가 flush를 한다.
        // 이 개념을 Dirty Checking이라 한다.
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(posts);
    }

    // Transaction을 유지하되, 조회 기능만 남겨두기 때문에 조회속도가 개선된다.
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}
