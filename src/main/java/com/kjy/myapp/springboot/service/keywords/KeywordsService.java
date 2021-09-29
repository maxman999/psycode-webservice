package com.kjy.myapp.springboot.service.keywords;

import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.web.dto.KeywordsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class KeywordsService {
    private final KeywordsRepository keywordsRepository;

    @Transactional
    public Long save(KeywordsSaveRequestDto requestDto) {
        System.out.println(requestDto.toEntity().getUser().getEmail());
        return keywordsRepository.save(requestDto.toEntity()).getId();
    }

//    @Transactional
//    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//        Keywords keywords = keywordsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
//        keywords.update(requestDto.getTitle(), requestDto.getContent());
//        return id;
//    }


}
