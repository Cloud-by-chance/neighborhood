package com.example.nuvi_demo.web.dto.reply;

import com.example.nuvi_demo.domain.reply.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@DynamicInsert
public class ReplySaveRequestDto {
    private int is_del;
    @Column(columnDefinition = "text")
    private String content;
    private String user_id;
    private Long post_id;
    private Long board_id;

    @Builder
    public ReplySaveRequestDto(String content, String user_id, Long post_id, Long board_id) {
        this.user_id = user_id;
        this.content = content;
        this.post_id = post_id;
        this.board_id = board_id;
    }

    public Reply toEntity() {
        return Reply.builder()
                .content(content)
                .post_id(post_id)
                .board_id(board_id)
                .user_id(user_id)
                .build();
    }
}
