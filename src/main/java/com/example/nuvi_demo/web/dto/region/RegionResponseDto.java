package com.example.nuvi_demo.web.dto.region;

import com.example.nuvi_demo.domain.region.Region;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
public class RegionResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long region_id;
    private String region_depth1;
    private String region_depth2;
    private String region_depth3;
    private String region_depth4;

    @Builder
    public RegionResponseDto(Region entity) {
        this.region_id = entity.getRegion_id();
        this.region_depth1 = entity.getRegion_depth1();
        this.region_depth2 = entity.getRegion_depth2();
        this.region_depth3 = entity.getRegion_depth3();
        this.region_depth4 = entity.getRegion_depth4();
    }
}
