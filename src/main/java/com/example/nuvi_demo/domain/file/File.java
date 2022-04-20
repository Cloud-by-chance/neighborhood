package com.example.nuvi_demo.domain.file;

import com.example.nuvi_demo.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String org_name;
    private Long byte_size;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int is_del;
    private String save_path;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Long post_id;
    private Long board_id;
    // N : 1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable=false, updatable=false)
    private Post Post;

    @Builder
    public File(String name, String org_name, Long byte_size, String save_path, Long post_id, Long board_id) {
        this.name = name;
        this.org_name = org_name;
        this.byte_size = byte_size;
        this.save_path = save_path;
        this.post_id = post_id;
        this.board_id = board_id;
    }

    public void update(String name, String org_name, Long byte_size, String save_path) {
        this.name = name;
        this.org_name = org_name;
        this.byte_size = byte_size;
        this.save_path = save_path;
    }

    @Override
    public String toString() {
        return "name : " + name + "\n" +
                "org_name : " + org_name + "\n" +
                "byte_size : " + byte_size + "\n" +
                "save_path : " + save_path + "\n" +
                "post_id : " + post_id + "\n" +
                "board_id : " + board_id + "\n";
    }
}
