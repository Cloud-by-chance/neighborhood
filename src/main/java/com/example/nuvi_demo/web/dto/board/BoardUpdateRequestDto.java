package com.example.nuvi_demo.web.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "BOARD")
public class BoardUpdateRequestDto {
    private String board_name;
    private String board_type;
    private int region_id;

    @Builder
    public BoardUpdateRequestDto(String board_name, String board_type, int region_id) {
        this.board_name = board_name;
        this.board_type = board_type;
        this.region_id = region_id;
    }
}
