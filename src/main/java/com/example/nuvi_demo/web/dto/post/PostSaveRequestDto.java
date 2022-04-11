package com.example.nuvi_demo.web.dto.post;

import com.example.nuvi_demo.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "POST")
public class PostSaveRequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    private Long board_id;
    private String post_name;
    private String user_id;
    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public PostSaveRequestDto(Long board_id, String post_name, String user_id, String content) {
        this.board_id = board_id;
        this.post_name = post_name;
        this.user_id = user_id;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .post_name(post_name)
                .board_id(board_id)
                .user_id(user_id)
                .content(content)
                .build();
    }
}
