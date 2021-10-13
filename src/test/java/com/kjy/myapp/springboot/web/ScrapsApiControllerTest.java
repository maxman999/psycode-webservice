package com.kjy.myapp.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjy.myapp.springboot.domain.scraps.Scraps;
import com.kjy.myapp.springboot.domain.scraps.ScrapsRepository;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import com.kjy.myapp.springboot.web.dto.ScrapsSaveRequestDto;
import com.kjy.myapp.springboot.web.dto.ScrapsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// For mockMvc

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScrapsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScrapsRepository scrapsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User userTest = User.builder()
                .picture("testImg")
                .email("testEmail")
                .name("testName")
                .role(Role.USER)
                .build();
        userRepository.save(userTest);
    }

    @After
    public void tearDown() throws Exception {
        scrapsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void scraps_register() throws Exception {
        //given
        String title = "title";
        String description = "description";
        String summary = "summary";
        String pubdate = "2021-09-23";
        String originallink = "www.kjy.com";
        User user = userRepository.findAll().get(0);

        ScrapsSaveRequestDto requestDto = ScrapsSaveRequestDto.builder()
                .title(title)
                .description(description)
                .useremail(user.getEmail())
                .summary(summary)
                .pubdate(pubdate)
                .originallink(originallink)
                .build();

        String url = "http://localhost:" + port + "/api/v1/scraps";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Scraps> all = scrapsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getDescription()).isEqualTo(description);
    }

    @Test
    @WithMockUser(roles="USER")
    public void scraps_update() throws Exception {
        //given
        User user = userRepository.findAll().get(0);
        Scraps savedScraps = scrapsRepository.save(Scraps.builder()
                .title("title")
                .description("description")
                .summary("summary")
                .originallink("www.kjy.com")
                .pubdate("2021-09-23")
                .user(user)
                .build());

        Long updateId = savedScraps.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        ScrapsUpdateRequestDto requestDto = ScrapsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/scraps/" + updateId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Scraps> all = scrapsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getDescription()).isEqualTo(expectedContent);
    }
}