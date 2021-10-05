package com.kjy.myapp.springboot.service;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.posts.PostsRepository;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import com.kjy.myapp.springboot.service.posts.PostsService;
import com.kjy.myapp.springboot.web.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

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
        postsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testInsert(){
        User user = userRepository.findAll().get(0);
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title("title")
                .description("content")
                .pubdate("2021-09-23")
                .originallink("www.kjy.com")
                .summary("test")
                .useremail(user.getEmail())
                .build();
        postsService.save(requestDto);
        //when
        List<PostsListResponseDto> postList = postsService.findAllDesc();
        //then
        PostsListResponseDto postsListResponseDto = postList.get(0);
        assertThat(postsListResponseDto.getTitle()).isEqualTo("title");
        assertThat(postsListResponseDto.getUser().getName()).isEqualTo(user.getName());
    }

    @Test
    public void testGetListWithPaging(){
        PageRequestDto pageRequestDTO = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDto<PostsListResponseDto, Posts> resultDTO = postsService.getListWithPaging(pageRequestDTO, "testEmail");

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());
        System.out.println("postsDTO-----------------------------------------");
        for (PostsListResponseDto postsListResponseDto : resultDTO.getDtoList()){
            System.out.println(postsListResponseDto);
        }
        System.out.println("페이지 번호-----------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.print(i+" "));

    }
    @Test
    public void testSerch(){
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("title")
                .build();
        PageResultDto<PostsListResponseDto, Posts> resultDTO = postsService.getListWithPaging(pageRequestDto, "testEmail");

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());
        System.out.println("postsDTO-----------------------------------------");
        for (PostsListResponseDto postsListResponseDto : resultDTO.getDtoList()){
            System.out.println(postsListResponseDto);
        }
        System.out.println("페이지 번호-----------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.print(i+" "));
    }
}
