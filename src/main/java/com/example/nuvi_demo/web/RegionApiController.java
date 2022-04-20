package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.post.PostService;
import com.example.nuvi_demo.service.region.RegionService;
import com.example.nuvi_demo.web.dto.post.PostResponseDto;
import com.example.nuvi_demo.web.dto.post.PostSaveRequestDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
import com.example.nuvi_demo.web.dto.region.RegionResponseDto;
import com.example.nuvi_demo.web.dto.region.RegionSaveRequestDto;
import com.example.nuvi_demo.web.dto.region.RegionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegionApiController {
    private final RegionService regionService;

    @PostMapping("/api/v1/region")
    public Long save(@RequestBody RegionSaveRequestDto requestDto) {
        return regionService.save(requestDto);
    }

    @PutMapping("/api/v1/region/{region_id}")
    public Long update(@PathVariable Long region_id, @RequestBody RegionUpdateRequestDto requestDto) {
        return regionService.update(region_id, requestDto);
    }

    @GetMapping("/api/v1/region/{region_id}")
    public RegionResponseDto findById(@PathVariable("region_id") Long region_id) {
        return regionService.findById(region_id);
    }
}
