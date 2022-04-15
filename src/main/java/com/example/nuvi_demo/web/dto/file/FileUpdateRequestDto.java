package com.example.nuvi_demo.web.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Table;

@Getter
@NoArgsConstructor
@DynamicUpdate
public class FileUpdateRequestDto {
    private String name;
    private String org_name;
    private Long byte_size;
    private String save_path;

    @Builder
    public FileUpdateRequestDto(String name, String org_name, Long byte_size, String save_path) {
        this.name = name;
        this.org_name = org_name;
        this.byte_size = byte_size;
        this.save_path = save_path;
    }
}
