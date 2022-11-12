package com.kjy.myapp.springboot.service.keywords;

import com.kjy.myapp.springboot.domain.keywords.Keywords;
import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.web.dto.KeywordsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KeywordsService {
    private final KeywordsRepository keywordsRepository;

    @Transactional
    public Long save(KeywordsSaveRequestDto requestDto) {
        Optional<Keywords> opt = keywordsRepository.findByUser_email(requestDto.getUserEmail());
        if(!opt.isPresent()){
            return keywordsRepository.save(requestDto.toEntity()).getId();
        }else {
            Keywords keywords = opt.get();
            keywords.update(requestDto.getKeyword1(), requestDto.getKeyword2(), requestDto.getKeyword3());
            return keywords.getId();
        }
    }

    public List getKeywordList(String user_email){
        Keywords keywords;
        List<String> keyList = new ArrayList<>();
        Optional opt = keywordsRepository.findByUser_email(user_email);
        if(opt.isPresent()){
            keywords = (Keywords) opt.get();
            keyList.add(keywords.getKeyword1());
            keyList.add(keywords.getKeyword2());
            keyList.add(keywords.getKeyword3());
        }
        return keyList;
    }


}
