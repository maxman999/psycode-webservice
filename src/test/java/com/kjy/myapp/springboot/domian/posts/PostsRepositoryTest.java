package com.kjy.myapp.springboot.domian.posts;

import com.kjy.myapp.springboot.domain.keywords.KeywordsRepository;
import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.posts.PostsRepository;
import com.kjy.myapp.springboot.domain.posts.QPosts;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import com.kjy.myapp.springboot.web.dto.PostsSaveRequestDto;
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
public class PostsRepositoryTest {

    @Autowired
    private KeywordsRepository keywordsRepository;

    @Autowired
    private PostsRepository postsRepository;

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
        postsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testInsertAndSelect() {
        //given
        String title = "테스트 게시글";
        String description = "테스트 본문";
        User user = userRepository.findAll().get(0);
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .description(description)
                .pubdate("2021-09-23")
                .originallink("www.kjy.com")
                .summary("test")
                .useremail(user.getEmail())
                .build();

        postsRepository.save(requestDto.toEntity());
        System.out.println(requestDto.toEntity().getDescription());

        //when
        List<Posts> postList = postsRepository.findAll();
        //then
        Posts posts = postList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getDescription()).isEqualTo(description);
        assertThat(posts.getUser().getEmail()).isEqualTo(user.getEmail());
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

        Posts posts = Posts.builder()
                .title(title)
                .description(description)
                .summary(summary)
                .originallink(originallink)
                .pubdate(pubdate)
                .user(user)
                .build();
        System.out.println("before update: " + postsRepository.save(posts).getDescription());
        assertThat(posts.getDescription()).isEqualTo(description);
        posts.update(title, description2);
        System.out.println("after update: " + postsRepository.save(posts).getDescription());
        assertThat(posts.getDescription()).isEqualTo(description2);
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

        Posts posts = Posts.builder()
                .title(title)
                .description(description)
                .summary(summary)
                .originallink(originallink)
                .pubdate(pubdate)
                .user(user)
                .build();
        System.out.println("before delete : " + postsRepository.save(posts).getDescription());
        postsRepository.deleteAll();
        assertThat(postsRepository.findAll()).isEmpty();
    }

    @Test
    public void testBaseTimeEntityInsert() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        User user = userRepository.findAll().get(0);

        postsRepository.save(Posts.builder()
                .title("title")
                .description("content")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user)
                .build());
        //when
        List<Posts> postList = postsRepository.findAll();

        //then
        Posts posts = postList.get(0);
        System.out.println(">>>>>> createData = " + posts.getCreateDate() + ", modifiedData = " + posts.getModifiedDate());

        assertThat(posts.getCreateDate().isAfter(now));
        assertThat(posts.getModifiedDate().isAfter(now));
    }

    @Test
    public void testInsertDummies() {
        User user = userRepository.findAll().get(0);

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Posts posts = Posts.builder()
                    .title("title")
                    .description("content")
                    .summary("summary")
                    .originallink("www.kjy.com")
                    .pubdate("2021-09-23")
                    .user(user)
                    .build();
            postsRepository.save(posts);
        });
    }

    @Test
    public void testPageDefault() {
        //1페이지 10개
        Pageable pageable = PageRequest.of(0, 10);
        Page<Posts> result = postsRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("--------------------------------");
        System.out.println("Total Pages : " + result.getTotalPages()); // 전체 페이지 수
        System.out.println("Total Count : " + result.getTotalElements()); // 전체 개수
        System.out.println("Page Number : " + result.getNumber()); //현재 페이지 번호
        System.out.println("Page Size : " + result.getSize()); // 페이지 당 데이터 개수
        System.out.println("has next page? : " + result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("first page? : " + result.isFirst()); // 시작 페이지(0) 여부
        System.out.println("--------------------------------");
        for (Posts posts : result.getContent()) {
            System.out.println(posts);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Posts> result = postsRepository.findAll(pageable);
        result.get().forEach(post -> {
            System.out.println(post);
        });
    }

    @Test
    public void testQuerydsl() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QPosts qPosts = QPosts.posts;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qPosts.title.contains(keyword);
        builder.and(expression);
        Page<Posts> result = postsRepository.findAll(builder, pageable);

        result.stream().forEach(post -> {
            System.out.println(post);
        });
    }

    @Test
    public void testQuerydsl2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QPosts qPosts = QPosts.posts;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qPosts.title.contains(keyword);
        BooleanExpression exContent = qPosts.description.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qPosts.id.gt(0L));
        Page<Posts> result = postsRepository.findAll(builder, pageable);
        result.stream().forEach(post -> {
            System.out.println(post);
        });
    }

    @Test
    public void testCheck(){
        User user = userRepository.findAll().get(0);
        Posts posts = Posts.builder()
                .title("title")
                .description("content")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user)
                .build();
        postsRepository.save(posts);

        User user2 = User.builder()
                .picture("testImg2")
                .email("testEmail2")
                .name("testName2")
                .role(Role.USER)
                .build();
        userRepository.save(user2);
        Posts posts2 = Posts.builder()
                .title("title")
                .description("content")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user2)
                .build();
        postsRepository.save(posts2);

        Posts result = postsRepository.check(posts.getTitle(),user.getEmail());
        System.out.println("title1 : "  + result.getTitle());
        System.out.println("user1 : "  + result.getUser().getEmail());
        Posts result2 = postsRepository.check(posts2.getTitle(),user2.getEmail());
        System.out.println("title2 : "  + result2.getTitle());
        System.out.println("user2 : "  + result2.getUser().getEmail());

        assertThat(result.getTitle()).isEqualTo(result2.getTitle());
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(result2.getUser().getEmail()).isEqualTo(user2.getEmail());
        assertThat(result.getUser().getEmail()).isNotEqualTo(result2.getUser().getEmail());



    }

}