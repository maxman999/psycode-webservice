package com.kjy.myapp.springboot.service.scraps;

import com.kjy.myapp.springboot.domain.scraps.QScraps;
import com.kjy.myapp.springboot.domain.scraps.Scraps;
import com.kjy.myapp.springboot.domain.scraps.ScrapsRepository;
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
public class ScrapsService {
    private final ScrapsRepository scrapsRepository;

    @Transactional
    public Long save(ScrapsSaveRequestDto requestDto) {
        return scrapsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ScrapsUpdateRequestDto requestDto) {
        Scraps scraps = scrapsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        scraps.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }
    public ScrapsResponseDto findById(Long id) {
        Scraps entity = scrapsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new ScrapsResponseDto(entity);
    }
    @Transactional(readOnly = true)
    public List<ScrapsListResponseDto> findAllDesc() {
        return scrapsRepository.findAllDESC().stream()
                .map(ScrapsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional()
    public void delete(Long id) {
        Scraps scraps = scrapsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        scrapsRepository.delete(scraps);
    }
    @Transactional(readOnly = true)
    public PageResultDto<ScrapsListResponseDto, Scraps> getListWithPaging(PageRequestDto requestDTO, String email) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO, email); // 검색 처리
        Page<Scraps> result = scrapsRepository.findAll(booleanBuilder, pageable); // querydsl 적용
        Function<Scraps, ScrapsListResponseDto> fn = (scraps -> new ScrapsListResponseDto(scraps));
        return new PageResultDto<>(result, fn);
    }
    private BooleanBuilder getSearch(PageRequestDto requestDto, String email) {
        // Querydsl 처리
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QScraps qScraps = QScraps.scraps;
        String keyword = requestDto.getKeyword();
        BooleanExpression expression = qScraps.user.email.contains(email); // 특정 아이디의 스크랩만 조회
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }
        // 검색 조건을 작성하기
        BooleanBuilder conditionalBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionalBuilder.or(qScraps.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionalBuilder.or(qScraps.description.contains(keyword));
        }
        if (type.contains("w")) {
            conditionalBuilder.or(qScraps.user.name.contains(keyword));
        }
        // 모든 조건 통합
        booleanBuilder.and(conditionalBuilder);
        return booleanBuilder;
    }
    public boolean check(String title, String user_email) {
        Scraps entity = scrapsRepository.check(title, user_email);
        boolean chk = (entity == null) ? false : true;
        return chk;
    }
}