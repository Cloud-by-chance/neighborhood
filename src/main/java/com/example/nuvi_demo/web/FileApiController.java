package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.file.FileService;
import com.example.nuvi_demo.web.dto.file.FileResponseDto;
import com.example.nuvi_demo.web.dto.file.FileSaveRequestDto;
import com.example.nuvi_demo.web.dto.file.FileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FileApiController {
    private final FileService fileService;

    @PostMapping("/api/v1/file")
    public Long save(@RequestBody FileSaveRequestDto requestDto) {
        return fileService.save(requestDto);
    }

    @PutMapping("/api/v1/file/{file_id}")
    public Long update(@PathVariable Long file_id, @RequestBody FileUpdateRequestDto requestDto) {
        return fileService.update(file_id, requestDto);
    }

    @GetMapping("/api/v1/file/{file_id}")
    public FileResponseDto findById(@PathVariable("file_id") Long file_id) {
        return fileService.findById(file_id);
    }
}
