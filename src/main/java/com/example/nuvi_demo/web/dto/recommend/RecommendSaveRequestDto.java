package com.example.nuvi_demo.web.dto.recommend;

import com.example.nuvi_demo.domain.recommend.Recommend;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicInsert
public class RecommendSaveRequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long cnt;
    private String user_id;
    private Long post_id;
    private Long board_id;
    private Long reply_id;

    @Builder
    public RecommendSaveRequestDto(Long cnt, String user_id, Long post_id, Long board_id, Long reply_id) {
        this.cnt = cnt;
        this.user_id = user_id;
        this.post_id = post_id;
        this.board_id = board_id;
        this.reply_id = reply_id;
    }

    public Recommend toEntity() {
        return Recommend.builder()
                .cnt(cnt)
                .user_id(user_id)
                .post_id(post_id)
                .board_id(board_id)
                .reply_id(reply_id)
                .build();
    }
}
