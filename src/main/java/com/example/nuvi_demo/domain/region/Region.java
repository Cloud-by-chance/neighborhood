package com.example.nuvi_demo.domain.region;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "REGION")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long region_id;
    private String region_depth1;
    private String region_depth2;
    private String region_depth3;
    private String region_depth4;

    @Builder
    public Region(Long region_id, String region_depth1, String region_depth2, String region_depth3, String region_depth4) {
        this.region_id = region_id;
        this.region_depth1 = region_depth1;
        this.region_depth2 = region_depth2;
        this.region_depth3 = region_depth3;
        this.region_depth4 = region_depth4;
    }
}
