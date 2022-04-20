package com.example.nuvi_demo.service.reply;

import com.example.nuvi_demo.domain.reply.Reply;
import com.example.nuvi_demo.domain.reply.ReplyRepository;
import com.example.nuvi_demo.web.dto.reply.ReplyResponseDto;
import com.example.nuvi_demo.web.dto.reply.ReplySaveRequestDto;
import com.example.nuvi_demo.web.dto.reply.ReplyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Long save(ReplySaveRequestDto requestDto) {
        return replyRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional
    public Long update(Long idx, ReplyUpdateRequestDto requestDto) {
        Reply reply = replyRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));

        reply.update(requestDto.getContent(), requestDto.getUser_id(), requestDto.getPost_id(), requestDto.getBoard_id());
        return idx;
    }

    public ReplyResponseDto findById(Long idx) {
        Reply entity = replyRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));

        return new ReplyResponseDto(entity);
    }
}
