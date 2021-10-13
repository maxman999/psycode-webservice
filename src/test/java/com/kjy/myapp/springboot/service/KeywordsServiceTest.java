package com.kjy.myapp.springboot.service;

import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import com.kjy.myapp.springboot.service.keywords.KeywordsService;
import com.kjy.myapp.springboot.web.dto.KeywordsSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordsServiceTest {

    @Autowired
    private KeywordsService keywordsServices;

    @Autowired
    UserRepository userRepository;

    @Autowired
    KeywordsRepository keywordsRepository;

    @Before
    public void setup(){
        User user = User.builder()
                .picture("testImg")
                .email("testEmail")
                .name("testName")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    @After
    public void cleanup() {
        keywordsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSave(){
        String key1 = "인공지능";
        String key2 = "연예";
        String key3 = "정치";
        String cKey1 = "시사";

        User user = userRepository.findByEmail("testEmail").get();

        KeywordsSaveRequestDto keywordsSaveRequestDto = KeywordsSaveRequestDto.builder()
                .keyword1_user(key1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .useremail(user.getEmail())
                .build();

        Long before_id = keywordsServices.save(keywordsSaveRequestDto);
        String before_key = keywordsRepository.findByUser_email("testEmail").get().getKeyword1_user();

        System.out.println("before id : " + before_id);
        System.out.println("before key : " + before_key);

        KeywordsSaveRequestDto keywordsSaveRequestDto2 = KeywordsSaveRequestDto.builder()
                .keyword1_user(cKey1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .useremail(user.getEmail())
                .build();

        Long after_id = keywordsServices.save(keywordsSaveRequestDto2);
        String after_key = keywordsRepository.findByUser_email("testEmail").get().getKeyword1_user();
        System.out.println("after id : " + after_id);
        System.out.println("after key : " + after_key);

        assertThat(after_id).isEqualTo(before_id);
        assertThat(after_key).isEqualTo(cKey1);
    }
}

