package com.example.nuvi_demo.web.dto.reply;

import com.example.nuvi_demo.domain.reply.Reply;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name="REPLY")
public class ReplyResponseDto {
    @Column(columnDefinition = "text")
    private String content;
    private String user_id;
    private Long post_id;
    private Long board_id;

    @Builder
    public ReplyResponseDto(Reply entity) {
        this.content = entity.getContent();
        this.user_id = entity.getUser_id();
        this.post_id = entity.getPost_id();
        this.board_id = entity.getBoard_id();
    }
}
