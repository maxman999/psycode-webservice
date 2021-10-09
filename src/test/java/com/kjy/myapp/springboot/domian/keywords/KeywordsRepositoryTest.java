package com.kjy.myapp.springboot.domian.keywords;

import com.kjy.myapp.springboot.domain.keywords.Keywords;
import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import com.kjy.myapp.springboot.web.dto.KeywordsSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordsRepositoryTest {

    @Autowired
    private KeywordsRepository keywordsRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        User userTest = User.builder()
                .picture("testImg")
                .email("testEmail")
                .name("testName")
                .role(Role.USER)
                .build();
        userRepository.save(userTest);
    }

    @After
    public void cleanup() {
        keywordsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testInsertAndGetKeywords() {
        //given
        String key1 = "인공지능";
        String key2 = "연예";
        String key3 = "정치";
        User user = userRepository.findByEmail("testEmail").get();

        KeywordsSaveRequestDto keywordsSaveRequestDto = KeywordsSaveRequestDto.builder()
                .keyword1_user(key1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .useremail(user.getEmail())
                .build();

        keywordsRepository.save(keywordsSaveRequestDto.toEntity());

        //when
        List<Keywords> keywordsList = keywordsRepository.findAll();

        //then
        Keywords keywords = keywordsList.get(0);
        assertThat(keywords.getKeyword1_user()).isEqualTo(key1);
        assertThat(keywords.getKeyword2_user()).isEqualTo(key2);
        assertThat(keywords.getKeyword3_user()).isEqualTo(key3);
        assertThat(keywords.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testUpdate() {
        String key1 = "인공지능";
        String key2 = "연예";
        String key3 = "정치";

        String cKey1 = "시사";
        String cKey2 = "교양";
        String cKey3 = "문화";

        User user = userRepository.findByEmail("testEmail").get();

        KeywordsSaveRequestDto keywordsSaveRequestDto = KeywordsSaveRequestDto.builder()
                .keyword1_user(key1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .useremail(user.getEmail())
                .build();

        Keywords keywords = Keywords.builder()
                .keyword1_user(key1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .user(user)
                .build();

        System.out.println("before update: " + keywordsRepository.save(keywords).getKeyword1_user());
        assertThat(keywords.getKeyword1_user()).isEqualTo(key1);
        keywords.update(cKey1,cKey2,cKey3);
        System.out.println("after update: " + keywordsRepository.save(keywords).getKeyword1_user());
        assertThat(keywords.getKeyword1_user()).isEqualTo(cKey1);
    }

    @Test
    public void testFindByEmail() {
        String key1 = "인공지능";
        String key2 = "연예";
        String key3 = "정치";

        User user = userRepository.findByEmail("testEmail").get();

        KeywordsSaveRequestDto keywordsSaveRequestDto = KeywordsSaveRequestDto.builder()
                .keyword1_user(key1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .useremail(user.getEmail())
                .build();

        Keywords keywords = Keywords.builder()
                .keyword1_user(key1)
                .keyword2_user(key2)
                .keyword3_user(key3)
                .user(user)
                .build();

        keywords = keywordsRepository.save(keywords);
        assertThat(keywordsRepository.findByUser_email("testEmail").get().getId()).isEqualTo(keywords.getId());
    }
}
