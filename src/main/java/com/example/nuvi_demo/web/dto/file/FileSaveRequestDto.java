package com.example.nuvi_demo.web.dto.file;

import com.example.nuvi_demo.domain.file.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicInsert
public class FileSaveRequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String org_name;
    private Long byte_size;
    private String save_path;
    private Long post_id;
    private Long board_id;

    @Builder
    public FileSaveRequestDto(String name, String org_name, Long byte_size, String save_path, Long post_id, Long board_id) {
        this.name = name;
        this.org_name = org_name;
        this.byte_size = byte_size;
        this.save_path = save_path;
        this.post_id = post_id;
        this.board_id = board_id;
    }

    public File toEntity() {
        return File.builder()
                .name(name)
                .org_name(org_name)
                .byte_size(byte_size)
                .save_path(save_path)
                .post_id(post_id)
                .board_id(board_id)
                .build();
    }
}
