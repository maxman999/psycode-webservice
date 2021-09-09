package com.kjy.myapp.springboot.service;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.service.posts.PostsService;
import com.kjy.myapp.springboot.web.dto.PageRequestDto;
import com.kjy.myapp.springboot.web.dto.PageResultDto;
import com.kjy.myapp.springboot.web.dto.PostsListResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Test
    public void testGetListWithPaging(){
        PageRequestDto pageRequestDTO = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDto<PostsListResponseDto, Posts> resultDTO = postsService.getListWithPaging(pageRequestDTO);

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
