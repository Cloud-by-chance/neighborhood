package com.example.nuvi_demo.service.region;

import com.example.nuvi_demo.domain.region.Region;
import com.example.nuvi_demo.domain.region.RegionRepository;
import com.example.nuvi_demo.web.dto.region.RegionResponseDto;
import com.example.nuvi_demo.web.dto.region.RegionSaveRequestDto;
import com.example.nuvi_demo.web.dto.region.RegionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    @Transactional
    public Long save(RegionSaveRequestDto requestDto) {
        return regionRepository.save(requestDto.toEntity()).getRegion_id();
    }

    @Transactional
    public Long update(Long region_id, RegionUpdateRequestDto requestDto) {
        Region region = regionRepository.findById(region_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

        region.update(requestDto.getRegion_depth1(), requestDto.getRegion_depth2(), requestDto.getRegion_depth3(), region.getRegion_depth4());
        return region_id;
    }

    public RegionResponseDto findById(Long region_id) {
        Region entity = regionRepository.findById(region_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장소입니다."));

        return new RegionResponseDto(entity);
    }
}
