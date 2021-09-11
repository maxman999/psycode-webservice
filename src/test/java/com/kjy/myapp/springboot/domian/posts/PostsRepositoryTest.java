package com.kjy.myapp.springboot.domian.posts;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.posts.PostsRepository;
import com.kjy.myapp.springboot.domain.posts.QPosts;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.After;
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
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void testInsertAndSelect() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "kjy@gmail.com";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        List<Posts> postList = postsRepository.findAll();

        //then
        Posts posts = postList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo(author);
    }

    @Test
    public void testUpdate() {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String content2 = "테스트 본문2";
        String author = "kjy@gmail.com";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        System.out.println("before update: " + postsRepository.save(posts).getContent());
        assertThat(posts.getContent()).isEqualTo(content);
        posts.update(title, content2);
        System.out.println("after update: " + postsRepository.save(posts).getContent());
        assertThat(posts.getContent()).isEqualTo(content2);
    }

    @Test
    public void testDelete() {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "kjy@gmail.com";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        System.out.println("before delete : " + postsRepository.save(posts).getContent());
        postsRepository.deleteAll();
        assertThat(postsRepository.findAll()).isEmpty();
    }

    @Test
    public void testBaseTimeEntityInsert() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
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
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Posts posts = Posts.builder()
                    .author("kjy")
                    .content("testContent" + i)
                    .title("testTitle" + i)
                    .build();
            postsRepository.save(posts);
        });
    }

    @Test
    public void testPageDefault(){
        //1페이지 10개
        Pageable pageable = PageRequest.of(0,10);
        Page<Posts> result = postsRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("--------------------------------");
        System.out.println("Total Pages : "+result.getTotalPages()); // 전체 페이지 수
        System.out.println("Total Count : "+result.getTotalElements()); // 전체 개수
        System.out.println("Page Number : "+result.getNumber()); //현재 페이지 번호
        System.out.println("Page Size : "+result.getSize()); // 페이지 당 데이터 개수
        System.out.println("has next page? : "+result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("first page? : "+result.isFirst()); // 시작 페이지(0) 여부
        System.out.println("--------------------------------");
        for (Posts posts : result.getContent()){
            System.out.println(posts);
        }
    }

    @Test
    public void testSort(){
        Sort sort1  = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0,10,sort1);
        Page<Posts> result = postsRepository.findAll(pageable);
        result.get().forEach(post -> {
            System.out.println(post);
        });
    }

    @Test
    public void testQuerydsl(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
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
    public void testQuerydsl2(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        QPosts qPosts = QPosts.posts;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qPosts.title.contains(keyword);
        BooleanExpression exContent = qPosts.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qPosts.id.gt(0L));
        Page<Posts> result = postsRepository.findAll(builder, pageable);
        result.stream().forEach(post -> {
            System.out.println(post);
        });
    }




}