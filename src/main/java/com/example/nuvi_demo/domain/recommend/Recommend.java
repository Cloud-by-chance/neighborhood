package com.example.nuvi_demo.domain.recommend;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "RECOMMEND")
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long cnt;
    private String user_id;
    private Long post_id;
    private Long board_id;
    private Long reply_id;

    @Builder
    public Recommend(Long cnt, String user_id, Long post_id, Long board_id, Long reply_id) {
        this.cnt = cnt;
        this.user_id = user_id;
        this.post_id = post_id;
        this.board_id = board_id;
        this.reply_id = reply_id;
    }

    public void update(Long cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "idx : " + idx + "\n" +
                "cnt : " + cnt + "\n" +
                "user_id : " + user_id + "\n" +
                "post_id : " + post_id + "\n" +
                "board_id : " + board_id + "\n" +
                "reply_id : " + reply_id;
    }
}
