package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.recommend.RecommendService;
import com.example.nuvi_demo.web.dto.recommend.RecommendResponseDto;
import com.example.nuvi_demo.web.dto.recommend.RecommendSaveRequestDto;
import com.example.nuvi_demo.web.dto.recommend.RecommendUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecommendApiController {
    private final RecommendService recommendService;

    @PostMapping("/api/v1/recommend")
    public Long save(@RequestBody RecommendSaveRequestDto requestDto) {
        return recommendService.save(requestDto);
    }

    @PutMapping("/api/v1/recommend/{idx}")
    public Long update(@PathVariable Long idx, @RequestBody RecommendUpdateRequestDto requestDto) {
        return recommendService.upate(idx, requestDto);
    }

    @GetMapping("/api/v1/recommend/{idx}")
    public RecommendResponseDto findById(@PathVariable("idx") Long idx) {
        return recommendService.findById(idx);
    }
}
