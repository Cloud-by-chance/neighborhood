package com.example.nuvi_demo.web.recommend;

import com.example.nuvi_demo.domain.recommend.Recommend;
import com.example.nuvi_demo.domain.recommend.RecommendRepository;
import com.example.nuvi_demo.web.dto.recommend.RecommendSaveRequestDto;
import com.example.nuvi_demo.web.dto.recommend.RecommendUpdateRequestDto;
import org.junit.After;
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
public class RecommendApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RecommendRepository recommendRepository;

    /*@After
    public void cleanup() {
        postRepository.deleteAll();
    }*/

    @Test
    @Transactional
    public void createTest() {
        // given
        String user_id = "test2";
        Long post_id = 8L;
        Long board_id = 5L;
//        Long reply_id = ;

        RecommendSaveRequestDto requestDto = RecommendSaveRequestDto.builder()
                .user_id(user_id)
                .post_id(post_id)
                .board_id(board_id)
                .build();

        String url = "http://localhost:" + port + "/api/v1/recommend";
        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Recommend> recommendList = recommendRepository.findAll();
        for(Recommend recommend : recommendList) {
            System.out.println(recommend);
        }
    }

    @Test
    @Transactional
    public void deleteTest() {
        Long idx = 1L;

        recommendRepository.deleteById(idx);
    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        Recommend savedRecommend = recommendRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 추천입니다."));

        Long updateIdx = savedRecommend.getIdx();
        Long expectedCnt = 10L;

        RecommendUpdateRequestDto requestDto = RecommendUpdateRequestDto.builder().cnt(expectedCnt).build();

        String url = "http://localhost:" + port + "/api/v1/recommend/" + updateIdx;

        HttpEntity<RecommendUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        // when
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // then
        readTest();

    }

    @Test
    public void readTest() {
        List<Recommend> recommendList = recommendRepository.findAll();
        for(Recommend recommend : recommendList) {
            System.out.println(recommend);
        }
    }
}
