package com.example.nuvi_demo.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;
    private String board_name;
    private String board_type;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int is_del;
    private int region_id;

    @Builder
    public Board(String board_name, String board_type, int region_id) {
        this.board_name = board_name;
        this.board_type = board_type;
        this.region_id = region_id;
    }

    public void update(String board_name, String board_type, int region_id) {
        this.board_name = board_name;
        this.board_type = board_type;
        this.region_id = region_id;
    }

    @Override
    public String toString() {
        return "board_name : " + board_name + "\n" +
                "board_type : " + board_type + " \n" +
                "region_id : " + region_id;
    }
}
