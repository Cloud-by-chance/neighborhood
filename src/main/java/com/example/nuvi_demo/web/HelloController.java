package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.post.PostService;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HelloController {
    private final PostService postService;

    @GetMapping("/")
    public String slash() {
        return "hello";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/api")
    public String index(Model model) {
        model.addAttribute("post", postService.findAllDesc());

        return "index";
    }

    @GetMapping("/api/post/save")
    public String postSave() {
        return "post-save";
    }

    @GetMapping("/api/post/update/{post_id}")
    public String postUpdate(@PathVariable Long post_id, Model model) {
        PostResponseDto dto = postService.findById(post_id);
        model.addAttribute("post", dto);
        System.out.println(model);
        return "post-update";
    }
}
