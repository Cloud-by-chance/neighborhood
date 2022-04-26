package com.example.nuvi_demo.domain.board;

import com.example.nuvi_demo.domain.post.Post;
import com.example.nuvi_demo.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;
    private String board_name;
    private String board_type;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int is_del;
    private int region_id;
    // 1 : N 관계
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> postList;
    // N : 1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable=false, updatable=false)
    private Region region;

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
