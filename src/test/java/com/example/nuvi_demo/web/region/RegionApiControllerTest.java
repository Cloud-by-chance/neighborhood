package com.example.nuvi_demo.web.region;

import com.example.nuvi_demo.domain.region.Region;
import com.example.nuvi_demo.domain.region.RegionRepository;
import com.example.nuvi_demo.web.dto.region.RegionSaveRequestDto;
import com.example.nuvi_demo.web.dto.region.RegionUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Commit
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegionApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private TestRestTemplate restTemplate;

   /* @After
    public void cleanup() {
        regionRepository.deleteAll();
    }*/

    @Test
    @Transactional
    public void createTest() {
        // given
        String region_depth1 = "서울시";
        String region_depth2 = "성동구";
        String region_depth3 = "상봉동";
        String region_depth4 = "홈플러스";
        String url = "http://localhost:" + port + "/api/v1/region";

        RegionSaveRequestDto requestDto = RegionSaveRequestDto.builder()
                .region_depth1(region_depth1)
                .region_depth2(region_depth2)
                .region_depth3(region_depth3)
                .region_depth4(region_depth4)
                .build();
        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        readTest();
    }

    @Test
    @Transactional
    public void deleteTest() {
        // given
        Long region_id = 4L;
        // when
        regionRepository.deleteById(region_id);
        // then
        readTest();
    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        Long region_id = 5L;
        Region savedRegion = regionRepository.findById(region_id).orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));
        String region_depth1 = "부산시";
        String region_depth2 = "해운대구";
        String region_depth3 = "반송동";
        String url = "http://localhost:" + port + "/api/v1/region/" + region_id;

        RegionUpdateRequestDto requestDto = RegionUpdateRequestDto.builder()
                .region_depth1(region_depth1)
                .region_depth2(region_depth2)
                .region_depth3(region_depth3)
                .build();

        HttpEntity<RegionUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        // when
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        readTest();
    }

    @Test
    public void readTest() {
        List<Region> regionList = regionRepository.findAll();

        for(Region region : regionList) {
            System.out.println(region);
        }
    }
}
