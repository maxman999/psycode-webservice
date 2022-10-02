package com.kjy.myapp.springboot.domian.scraps;

import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.domain.scraps.QScraps;
import com.kjy.myapp.springboot.domain.scraps.Scraps;
import com.kjy.myapp.springboot.domain.scraps.ScrapsRepository;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import com.kjy.myapp.springboot.web.dto.ScrapsSaveRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrapsRepositoryTest {

    @Autowired
    private KeywordsRepository keywordsRepository;

    @Autowired
    private ScrapsRepository scrapsRepository;

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
        scrapsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testInsertAndSelect() {
        //given
        String title = "테스트 게시글";
        String description = "테스트 본문";
        User user = userRepository.findAll().get(0);
        ScrapsSaveRequestDto requestDto = ScrapsSaveRequestDto.builder()
                .title(title)
                .description(description)
                .pubdate("2021-09-23")
                .originallink("www.kjy.com")
                .summary("test")
                .useremail(user.getEmail())
                .build();

        scrapsRepository.save(requestDto.toEntity());
        System.out.println(requestDto.toEntity().getDescription());

        //when
        List<Scraps> scrapList = scrapsRepository.findAll();
        //then
        Scraps scraps = scrapList.get(0);
        assertThat(scraps.getTitle()).isEqualTo(title);
        assertThat(scraps.getDescription()).isEqualTo(description);
        assertThat(scraps.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testUpdate() {
        String title = "테스트 게시글";
        String description = "테스트 본문";
        String description2 = "테스트 본문2";
        String summary = "summary";
        String originallink = "www.kjy.com";
        String pubdate = "2021-09-23";
        User user = userRepository.findAll().get(0);

        Scraps scraps = Scraps.builder()
                .title(title)
                .description(description)
                .summary(summary)
                .originallink(originallink)
                .pubdate(pubdate)
                .user(user)
                .build();
        System.out.println("before update: " + scrapsRepository.save(scraps).getDescription());
        assertThat(scraps.getDescription()).isEqualTo(description);
        scraps.update(title, description2);
        System.out.println("after update: " + scrapsRepository.save(scraps).getDescription());
        assertThat(scraps.getDescription()).isEqualTo(description2);
    }

    @Test
    public void testDelete() {
        String title = "테스트 게시글";
        String description = "테스트 본문";
        String description2 = "테스트 본문2";
        String summary = "summary";
        String originallink = "www.kjy.com";
        String pubdate = "2021-09-23";
        User user = userRepository.findAll().get(0);

        Scraps scraps = Scraps.builder()
                .title(title)
                .description(description)
                .summary(summary)
                .originallink(originallink)
                .pubdate(pubdate)
                .user(user)
                .build();
        System.out.println("before delete : " + scrapsRepository.save(scraps).getDescription());
        scrapsRepository.deleteAll();
        assertThat(scrapsRepository.findAll()).isEmpty();
    }

    @Test
    public void testBaseTimeEntityInsert() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        User user = userRepository.findAll().get(0);

        scrapsRepository.save(Scraps.builder()
                .title("title")
                .description("content")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user)
                .build());
        //when
        List<Scraps> scrapList = scrapsRepository.findAll();

        //then
        Scraps scraps = scrapList.get(0);
        System.out.println(">>>>>> createData = " + scraps.getCreateDate() + ", modifiedData = " + scraps.getModifiedDate());

        assertThat(scraps.getCreateDate().isAfter(now));
        assertThat(scraps.getModifiedDate().isAfter(now));
    }

    @Test
    public void testInsertDummies() {
        User user = userRepository.findAll().get(0);

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Scraps scraps = Scraps.builder()
                    .title("title")
                    .description("content")
                    .summary("summary")
                    .originallink("www.kjy.com")
                    .pubdate("2021-09-23")
                    .user(user)
                    .build();
            scrapsRepository.save(scraps);
        });
    }

    @Test
    public void testPageDefault() {
        //1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Scraps> result = scrapsRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("--------------------------------");
        System.out.println("Total Pages : " + result.getTotalPages()); // 전체 페이지 수
        System.out.println("Total Count : " + result.getTotalElements()); // 전체 개수
        System.out.println("Page Number : " + result.getNumber()); //현재 페이지 번호
        System.out.println("Page Size : " + result.getSize()); // 페이지 당 데이터 개수
        System.out.println("has next page? : " + result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("first page? : " + result.isFirst()); // 시작 페이지(0) 여부
        System.out.println("--------------------------------");
        for (Scraps scraps : result.getContent()) {
            System.out.println(scraps);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Scraps> result = scrapsRepository.findAll(pageable);
        result.get().forEach(scrap -> {
            System.out.println(scrap);
        });
    }

    @Test
    public void testQuerydsl() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QScraps qScraps = QScraps.scraps;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qScraps.title.contains(keyword);
        builder.and(expression);
        Page<Scraps> result = scrapsRepository.findAll(builder, pageable);

        result.stream().forEach(scrap -> {
            System.out.println(scrap);
        });
    }

    @Test
    public void testQuerydsl2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QScraps qScraps = QScraps.scraps;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qScraps.title.contains(keyword);
        BooleanExpression exContent = qScraps.description.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qScraps.id.gt(0L));
        Page<Scraps> result = scrapsRepository.findAll(builder, pageable);
        result.stream().forEach(scrap -> {
            System.out.println(scrap);
        });
    }

    @Test
    public void testCheck(){
        User user = userRepository.findAll().get(0);
        Scraps scraps = Scraps.builder()
                .title("title")
                .description("content")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user)
                .build();
        scrapsRepository.save(scraps);

        User user2 = User.builder()
                .picture("testImg2")
                .email("testEmail2")
                .name("testName2")
                .role(Role.USER)
                .build();
        userRepository.save(user2);
        Scraps scraps2 = Scraps.builder()
                .title("title")
                .description("content")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user2)
                .build();
        scrapsRepository.save(scraps2);

        Scraps result = scrapsRepository.check(scraps.getTitle(),user.getEmail());
        System.out.println("title1 : "  + result.getTitle());
        System.out.println("user1 : "  + result.getUser().getEmail());
        Scraps result2 = scrapsRepository.check(scraps2.getTitle(),user2.getEmail());
        System.out.println("title2 : "  + result2.getTitle());
        System.out.println("user2 : "  + result2.getUser().getEmail());

        assertThat(result.getTitle()).isEqualTo(result2.getTitle());
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(result2.getUser().getEmail()).isEqualTo(user2.getEmail());
        assertThat(result.getUser().getEmail()).isNotEqualTo(result2.getUser().getEmail());



    }

}