package com.example.nuvi_demo.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    private Long board_id;
    private String post_name;
    private String user_id;
    private int hits;
    private int reply_cnt;
    private int is_lock;
    private int reply_role;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int is_del;
    @Temporal(TemporalType.DATE)
    private Date create_dt;
    @Temporal(TemporalType.DATE)
    private Date update_dt;
    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public Post(String post_name, Long board_id, String user_id, int hits, int reply_cnt, int is_lock, int reply_role, String content) {
        this.post_name = post_name;
        this.board_id = board_id;
        this.user_id = user_id;
        this.hits = hits;
        this.reply_cnt = reply_cnt;
        this.is_lock = is_lock;
        this.reply_role = reply_role;
        this.content = content;
    }

    public void update(String post_name, String content) {
        this.post_name = post_name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "post name : " + post_name + "\n" +
                "user_id : " + user_id + "\n" +
                "content : " + content + "\n";
    }
}
