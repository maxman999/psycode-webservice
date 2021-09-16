package com.kjy.myapp.springboot.domian.user;

import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    public void testClass() {
        System.out.println("test class : " + userRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            User user = User.builder()
                    .picture("testImg"+i)
                    .email("testEmail"+i)
                    .name("testName"+i)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        });
    }

    @Test
    public void testSelect(){
        String email = "testEmail1";
        Optional<User> result = userRepository.findByEmail(email);
        System.out.println("=============================");
        if(result.isPresent()){
            User user = result.get();
            System.out.println(email + " : "+ user.toString());
        }
    }
}
