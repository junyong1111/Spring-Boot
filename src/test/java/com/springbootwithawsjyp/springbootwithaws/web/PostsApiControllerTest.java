package com.springbootwithawsjyp.springbootwithaws.web;
import com.springbootwithawsjyp.springbootwithaws.domain.posts.Posts;
import com.springbootwithawsjyp.springbootwithaws.domain.posts.PostsRepository;
import com.springbootwithawsjyp.springbootwithaws.web.dto.PostsSaveRequestDto;
import com.springbootwithawsjyp.springbootwithaws.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.rmi.server.ExportException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @Autowired@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;


    @AfterEach
    public void tearDown() throws ExportException{
        postsRepository.deleteAll();
    }
    private final String title = "title";
    private final String content = "content";
    private final String author = "author";

    @Test
    public void post_요청보내기(){

        String url = "http://localhost:" + port + "/api/v1/posts";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, long.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        List<Posts> postsList = postsRepository.findAll();
        assertEquals(postsList.get(0).getTitle(), title);
        assertEquals(postsList.get(0).getContent(), content);
        assertEquals(postsList.get(0).getAuthor(), author);
    }
    @Test
    public void posts_업데이트() throws Exception{
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        long updateId =savedPosts.getId();
        String expectedTitle = title;
        String expectedContent = content;

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        String url = "http://localhost:" + port +"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity, Long.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<Posts> postsList = postsRepository.findAll();
        assertEquals(postsList.get(0).getTitle(), expectedTitle);
        assertEquals(postsList.get(0).getContent(), expectedContent);
    }




}
