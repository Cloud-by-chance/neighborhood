package com.example.nuvi_demo.web.dto.board;

import com.example.nuvi_demo.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "BOARD")
public class BoardSaveRequestDto {
    private String board_name;
    private String board_type;
    private int region_id;

    @Builder
    public BoardSaveRequestDto(String board_name, String board_type, int region_id) {
        this.board_name = board_name;
        this.board_type = board_type;
        this.region_id = region_id;
    }

    public Board toEntity() {
        return Board.builder()
                .board_name(board_name)
                .board_type(board_type)
                .region_id(region_id)
                .build();
    }
}
