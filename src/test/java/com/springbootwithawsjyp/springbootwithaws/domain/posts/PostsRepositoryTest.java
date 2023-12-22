package com.springbootwithawsjyp.springbootwithaws.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

//@SpringBootTest
@DataJpaTest
public class PostsRepositoryTest {

//    @Mock
    @Autowired
    private PostsRepository postsRepositoryMock;
    @AfterEach
    public void cleanup(){
        postsRepositoryMock.deleteAll();;
    }
    String title = "테스트 게시글";
    String content = "테스트 본문";
    String author = "테스트 글쓴이";
    @Test
    public void 게시글_불러오기(){


        postsRepositoryMock.save(new Posts(title, content, author));
        List<Posts> postsList = postsRepositoryMock.findAll();
//        System.out.println(postsList);

    }
    @Test
    public void BaseTimeEntity_등록(){
        LocalDateTime now = LocalDateTime.of(2023, 12, 22,0,0,0);
        postsRepositoryMock.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        List<Posts> postsList = postsRepositoryMock.findAll();
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>>>>>createData=" +posts.getCreateDate()+", mpdifiedData="+posts.getModifiedDate());

        assertTrue(posts.getCreateDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));

    }

}
