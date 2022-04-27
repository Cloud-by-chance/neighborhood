package com.example.nuvi_demo.web.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@DynamicUpdate
public class ReplyUpdateRequestDto {
    @Column(columnDefinition = "text")
    private String content;
    private String user_id;
    private Long post_id;
    private Long board_id;

    @Builder
    public ReplyUpdateRequestDto(String content, String user_id, Long post_id, Long board_id) {
        this.user_id = user_id;
        this.content = content;
        this.post_id = post_id;
        this.board_id = board_id;
    }
}
