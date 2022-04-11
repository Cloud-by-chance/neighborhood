package com.example.nuvi_demo.web.dto.file;

import com.example.nuvi_demo.domain.file.File;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Table(name = "FILE")
public class FileResponseDto {
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
    @Temporal(TemporalType.DATE)
    private Date updated_at;
    private Long post_id;
    private Long board_id;

    @Builder
    public FileResponseDto(File entity) {
        this.name = entity.getName();
        this.org_name = entity.getOrg_name();
        this.byte_size = entity.getByte_size();
        this.save_path = entity.getSave_path();
        this.post_id = entity.getPost_id();
        this.board_id = entity.getBoard_id();
    }
}
