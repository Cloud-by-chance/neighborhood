package com.example.nuvi_demo.Controller;

import com.example.nuvi_demo.service.post.PostService;
import com.example.nuvi_demo.web.dto.post.PostListResponseDto;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import com.example.nuvi_demo.web.dto.post.PostSaveRequestDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin(origins = "*") //리액트와 연동하기 위한 CROS 설정
@Api(tags = {"1. Post"})
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = "application/json;charset=utf-8")
public class PostApiController {
    private final PostService postService;

    @GetMapping("/v1/posts")
    public List<PostListResponseDto> read() {
        return postService.findAllDesc();
    }

    @PostMapping("/v1/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/v1/post/{post_id}")
    public Long update(@PathVariable Long post_id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(post_id, requestDto);
    }

    @GetMapping("/v1/post/{post_id}")
    public PostResponseDto findById(@PathVariable("post_id") Long post_id) {
        return postService.findById(post_id);
    }

    @DeleteMapping("/v1/post/{post_id}")
    public Long delete(@PathVariable Long post_id) {
        postService.delete(post_id);
        return post_id;
    }
}
