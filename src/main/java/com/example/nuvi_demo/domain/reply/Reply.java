package com.example.nuvi_demo.domain.reply;

import com.example.nuvi_demo.domain.member.Member;
import com.example.nuvi_demo.domain.post.Post;
import com.example.nuvi_demo.domain.recommend.Recommend;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "REPLY")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private int is_del;
    @Column(columnDefinition = "text")
    private String content;
    private LocalDateTime create_dt;
    private LocalDateTime update_dt;
    private String user_id;
    private Long post_id;
    private Long board_id;
    // N : 1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    // 1: N 관계
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    private List<Recommend> recommendList;

    @Builder
    public Reply(String content, String user_id, Long post_id, Long board_id) {
        this.content = content;
        this.user_id = user_id;
        this.post_id = post_id;
        this.board_id = board_id;
    }

    public void update(String content, String user_id, Long post_id, Long board_id) {
        this.content = content;
        this.user_id = user_id;
        this.post_id = post_id;
        this.board_id = board_id;
    }

    @Override
    public String toString() {
        return "idx : " + idx + "\n" +
                "is_del : " + is_del + "\n" +
                "content : " + content + "\n" +
                "create_dt : " + create_dt + "\n" +
                "update_dt : " + update_dt + "\n" +
                "user_id : " + user_id + "\n" +
                "board_id : " + board_id + "\n" +
                "post_id : " + post_id + "\n";
    }
}
