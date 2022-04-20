package com.example.nuvi_demo.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicUpdate
public class PostUpdateRequestDto {
    private String post_name;
    private String content;

    @Builder
    public PostUpdateRequestDto(String post_name, String content) {
        this.post_name = post_name;
        this.content = content;
    }
}
