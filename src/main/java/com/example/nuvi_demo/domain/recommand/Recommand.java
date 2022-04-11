package com.example.nuvi_demo.domain.recommand;

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
@Table(name = "RECOMMAND")
public class Recommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long cnt;
    private String user_id;
    private Long post_id;
    private Long board_id;
    private Long reply_id;

    @Builder
    public Recommand(Long cnt, String user_id, Long post_id, Long board_id, Long reply_id) {
        this.cnt = cnt;
        this.user_id = user_id;
        this.post_id = post_id;
        this.board_id = board_id;
        this.reply_id = reply_id;
    }
}
