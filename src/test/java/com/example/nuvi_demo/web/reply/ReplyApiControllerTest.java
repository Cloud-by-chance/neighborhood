package com.example.nuvi_demo.web.reply;

import com.example.nuvi_demo.domain.reply.Reply;
import com.example.nuvi_demo.domain.reply.ReplyRepository;
import com.example.nuvi_demo.web.dto.reply.ReplySaveRequestDto;
import com.example.nuvi_demo.web.dto.reply.ReplyUpdateRequestDto;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplyApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

   /* @After
    public void cleanup() {
        replyRepository.deleteAll();
    }*/

    @Test
    @Transactional
    public void createTest() {
        // given
        String content = "This is first test reply";
        String user_id = "test2";
        Long post_id = 8L;
        Long board_id = 5L;
        String url = "http://localhost:" + port + "/api/v1/reply";

        ReplySaveRequestDto requestDto = ReplySaveRequestDto.builder()
                .content(content)
                .user_id(user_id)
                .post_id(post_id)
                .board_id(board_id)
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
        Long reply_id = 1L;
        // when
        replyRepository.deleteById(reply_id);
        // then
        readTest();
    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        Long reply_id = 1L;
        String expectedContent = "This one is update test content";
        String expectedUser_id = "test2";
        Long expectedPost_id = 8L;
        Long expectedBoard_id = 5L;
        String url = "http://localhost:" + port + "/api/v1/reply/" + reply_id;

        ReplyUpdateRequestDto requestDto = ReplyUpdateRequestDto.builder()
                .content(expectedContent)
                .user_id(expectedUser_id)
                .post_id(expectedPost_id)
                .board_id(expectedBoard_id)
                .build();

        HttpEntity<ReplyUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        // when
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // then
        readTest();
    }

    @Test
    public void readTest() {
        List<Reply> replyList = replyRepository.findAll();

        for(Reply reply : replyList) {
            System.out.println(reply);
        }
    }
}
