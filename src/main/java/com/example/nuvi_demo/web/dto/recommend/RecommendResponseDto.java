package com.example.nuvi_demo.web.dto.recommend;

import com.example.nuvi_demo.domain.recommend.Recommend;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
public class RecommendResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long cnt;
    private String user_id;
    private Long post_id;
    private Long board_id;
    private Long reply_id;

    @Builder
    public RecommendResponseDto(Recommend entity) {
        this.cnt = entity.getCnt();
        this.user_id = entity.getUser_id();
        this.post_id = entity.getPost_id();
        this.board_id = entity.getBoard_id();
        this.reply_id = entity.getReply_id();
    }
}
