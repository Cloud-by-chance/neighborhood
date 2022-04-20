package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.reply.ReplyService;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import com.example.nuvi_demo.web.dto.post.PostSaveRequestDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
import com.example.nuvi_demo.web.dto.reply.ReplyResponseDto;
import com.example.nuvi_demo.web.dto.reply.ReplySaveRequestDto;
import com.example.nuvi_demo.web.dto.reply.ReplyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/api/v1/reply")
    public Long save(@RequestBody ReplySaveRequestDto requestDto) {
        return replyService.save(requestDto);
    }

    @PutMapping("/api/v1/reply/{reply_id}")
    public Long update(@PathVariable Long reply_id, @RequestBody ReplyUpdateRequestDto requestDto) {
        return replyService.update(reply_id, requestDto);
    }

    @GetMapping("/api/v1/reply/{reply_id}")
    public ReplyResponseDto findById(@PathVariable("reply_id") Long reply_id) {
        return replyService.findById(reply_id);
    }
}
