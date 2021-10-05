package com.kjy.myapp.springboot.service.posts;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.posts.PostsRepository;
import com.kjy.myapp.springboot.domain.posts.QPosts;
import com.kjy.myapp.springboot.web.dto.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDESC().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResultDto<PostsListResponseDto, Posts> getListWithPaging(PageRequestDto requestDTO, String email) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO, email); // 검색 처리
        Page<Posts> result = postsRepository.findAll(booleanBuilder, pageable); // querydsl 적용
        Function<Posts, PostsListResponseDto> fn = (posts -> new PostsListResponseDto(posts));
        return new PageResultDto<>(result, fn);
    }

    @Transactional()
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto, String email) {
        // Querydsl 처리
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPosts qPosts = QPosts.posts;
        String keyword = requestDto.getKeyword();
        BooleanExpression expression = qPosts.user.email.contains(email); // 특정 아이디만 생성
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }
        // 검색 조건을 작성하기
        BooleanBuilder conditionalBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionalBuilder.or(qPosts.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionalBuilder.or(qPosts.description.contains(keyword));
        }
        if (type.contains("w")) {
            conditionalBuilder.or(qPosts.user.name.contains(keyword));
        }
        // 모든 조건 통합
        booleanBuilder.and(conditionalBuilder);
        return booleanBuilder;
    }

    public boolean check(String title) {
        Posts entity = postsRepository.check(title);
        boolean chk = (entity == null) ? false : true;
        return chk;
    }
}