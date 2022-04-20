package com.example.nuvi_demo.web.dto.board;

import com.example.nuvi_demo.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
public class BoardResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;
    private String board_name;
    private String board_type;
    private int region_id;

    @Builder
    public BoardResponseDto(Board entity) {
        this.board_id = entity.getBoard_id();
        this.board_name = entity.getBoard_name();
        this.board_type = entity.getBoard_type();
        this.region_id = entity.getRegion_id();
    }
}
