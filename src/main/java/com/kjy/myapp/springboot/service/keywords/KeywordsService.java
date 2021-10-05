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
        Optional<Keywords> opt = keywordsRepository.findByUser_email(requestDto.getUseremail());
        if(!opt.isPresent()){
            return keywordsRepository.save(requestDto.toEntity()).getId();
        }else {
            Keywords keywords = opt.get();
            keywords.update(requestDto.getKeyword1_user(), requestDto.getKeyword2_user(), requestDto.getKeyword3_user());
            return keywords.getId();
        }
    }

    public List getKeywordList(String user_email){
        Keywords keywords = new Keywords();
        List<String> keyList = new ArrayList<String>();
        Optional opt = keywordsRepository.findByUser_email(user_email);
        if(opt.isPresent()){
            keywords = (Keywords) opt.get();
            keyList.add(keywords.getKeyword1_user());
            keyList.add(keywords.getKeyword2_user());
            keyList.add(keywords.getKeyword3_user());
        }
        return keyList;
    }


}
