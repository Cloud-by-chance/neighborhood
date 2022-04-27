package com.example.nuvi_demo.web.dto.recommend;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicUpdate
public class RecommendUpdateRequestDto {
    private Long cnt;

    @Builder
    public RecommendUpdateRequestDto(Long cnt) {
        this.cnt = cnt;
    }
}
