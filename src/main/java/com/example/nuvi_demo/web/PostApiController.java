package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.member.MemberService;
import com.example.nuvi_demo.service.post.PostService;
import com.example.nuvi_demo.web.dto.member.MemberResponseDto;
import com.example.nuvi_demo.web.dto.member.MemberSaveRequestDto;
import com.example.nuvi_demo.web.dto.member.MemberUpdateRequestDto;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import com.example.nuvi_demo.web.dto.post.PostSaveRequestDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;

    @PostMapping("/api/v1/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/api/v1/post/{post_id}")
    public Long update(@PathVariable Long post_id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(post_id, requestDto);
    }

    @GetMapping("/api/v1/post/{post_id}")
    public PostResponseDto findById(@PathVariable("post_id") Long post_id) {
        return postService.findById(post_id);
    }

    @DeleteMapping("/api/v1/post/{post_id}")
    public Long delete(@PathVariable Long post_id) {
        postService.delete(post_id);
        return post_id;
    }
}
