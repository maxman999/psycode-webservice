package com.kjy.myapp.springboot.service.keywords;

import com.kjy.myapp.springboot.domain.keywords.Keywords;
import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.web.dto.KeywordsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KeywordsService {
    private final KeywordsRepository keywordsRepository;

    @Transactional
    public Long save(KeywordsSaveRequestDto requestDto) {
        Optional<Keywords> opt = keywordsRepository.findByUser_email(requestDto.getUseremail());
        if(!opt.isPresent()){
            return keywordsRepository.save(requestDto.toEntity()).getId();
        }else {
            Keywords keywords = opt.get();
            keywords.update(requestDto.getKeyword1_user(), requestDto.getKeyword2_user(), requestDto.getKeyword3_user());
            return keywords.getId();
        }
    }

//    @Transactional
//    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//        Keywords keywords = keywordsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
//        keywords.update(requestDto.getTitle(), requestDto.getContent());
//        return id;
//    }


}
