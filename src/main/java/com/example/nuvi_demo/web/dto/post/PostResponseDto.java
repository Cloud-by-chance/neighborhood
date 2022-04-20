package com.example.nuvi_demo.web.dto.post;

import com.example.nuvi_demo.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
public class PostResponseDto {
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
    public PostResponseDto(Post entity) {
        this.post_id = entity.getPost_id();
        this.post_name = entity.getPost_name();
        this.board_id = entity.getBoard_id();
        this.user_id = entity.getUser_id();
        this.hits = entity.getHits();
        this.reply_cnt = entity.getReply_cnt();
        this.is_lock = entity.getIs_lock();
        this.reply_role = entity.getReply_role();
        this.content = entity.getContent();
    }
}
