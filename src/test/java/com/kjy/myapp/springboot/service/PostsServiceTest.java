package com.kjy.myapp.springboot.service;

import com.kjy.myapp.springboot.service.posts.PostsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService service;

}
