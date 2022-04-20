package com.example.nuvi_demo.service.post;

import com.example.nuvi_demo.domain.post.Post;
import com.example.nuvi_demo.domain.post.PostRepository;
import com.example.nuvi_demo.web.dto.post.PostListResponseDto;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import com.example.nuvi_demo.web.dto.post.PostSaveRequestDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getPost_id();
    }

    @Transactional
    public Long update(Long post_id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        post.update(requestDto.getPost_name(), requestDto.getContent());
        return post_id;
    }

    public PostResponseDto findById(Long post_id) {
        Post entity = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return new PostResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        postRepository.delete(post);
    }
}
