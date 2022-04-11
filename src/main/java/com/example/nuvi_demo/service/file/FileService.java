package com.example.nuvi_demo.service.file;

import com.example.nuvi_demo.domain.file.File;
import com.example.nuvi_demo.domain.file.FileRepository;
import com.example.nuvi_demo.domain.post.Post;
import com.example.nuvi_demo.web.dto.file.FileResponseDto;
import com.example.nuvi_demo.web.dto.file.FileSaveRequestDto;
import com.example.nuvi_demo.web.dto.file.FileUpdateRequestDto;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Transactional
    public Long save(FileSaveRequestDto requestDto) {
        return fileRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long file_id, FileUpdateRequestDto requestDto) {
        File file = fileRepository.findById(file_id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        file.update(requestDto.getName(), requestDto.getOrg_name(), requestDto.getByte_size(), requestDto.getSave_path());
        return file_id;
    }

    public FileResponseDto findById(Long file_id) {
        File entity = fileRepository.findById(file_id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        return new FileResponseDto(entity);
    }
}
