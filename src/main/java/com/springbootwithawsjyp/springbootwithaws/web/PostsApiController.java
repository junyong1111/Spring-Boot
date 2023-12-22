package com.springbootwithawsjyp.springbootwithaws.web;

import com.springbootwithawsjyp.springbootwithaws.domain.posts.Posts;
import com.springbootwithawsjyp.springbootwithaws.domain.posts.PostsRepository;
import com.springbootwithawsjyp.springbootwithaws.service.PostsService;
import com.springbootwithawsjyp.springbootwithaws.web.dto.PostsResponseDto;
import com.springbootwithawsjyp.springbootwithaws.web.dto.PostsSaveRequestDto;
import com.springbootwithawsjyp.springbootwithaws.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostsApiController {
    private final PostsService postsService;


    @GetMapping(value = "/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable long id){
        return postsService.findById(id);
    }

    @PostMapping(value = "/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping(value = "/api/v1/posts/{id}")
    public Long update(@PathVariable long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);

    }


}
