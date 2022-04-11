package com.example.nuvi_demo.domain.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "FILE")
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
    @Temporal(TemporalType.DATE)
    private Date created_at;
    private Long post_id;
    private Long board_id;

    @Builder
    public File(String name, String org_name, Long byte_size, String save_path, Long post_id, Long board_id) {
        this.name = name;
        this.org_name = org_name;
        this.byte_size = byte_size;
        this.save_path = save_path;
        this.post_id = post_id;
        this.board_id = board_id;
    }
}
