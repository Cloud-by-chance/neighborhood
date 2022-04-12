package com.example.nuvi_demo.service.recommend;

import com.example.nuvi_demo.domain.recommend.Recommend;
import com.example.nuvi_demo.domain.recommend.RecommendRepository;
import com.example.nuvi_demo.web.dto.recommend.RecommendResponseDto;
import com.example.nuvi_demo.web.dto.recommend.RecommendSaveRequestDto;
import com.example.nuvi_demo.web.dto.recommend.RecommendUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;

    @Transactional
    public Long save(RecommendSaveRequestDto requestDto) {
        return recommendRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional
    public Long upate(Long idx, RecommendUpdateRequestDto requestDto) {
        Recommend recommend = recommendRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 추천은 존재하지 않습니다."));

        recommend.update(requestDto.getCnt());
        return idx;
    }

    public RecommendResponseDto findById(Long idx) {
        Recommend entity = recommendRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 추천은 존재하지 않습니다."));

        return new RecommendResponseDto(entity);
    }
}
