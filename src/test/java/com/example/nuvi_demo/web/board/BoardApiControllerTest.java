package com.example.nuvi_demo.web.board;

import com.example.nuvi_demo.domain.board.Board;
import com.example.nuvi_demo.domain.board.BoardRepository;
import com.example.nuvi_demo.web.dto.board.BoardSaveRequestDto;
import com.example.nuvi_demo.web.dto.board.BoardUpdateRequestDto;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Commit
public class BoardApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoardRepository boardRepository;

    @After
    public void cleanup() {
//        boardRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createTest() {
        // given
        String board_name = "First Test Board";
        String board_type = "게시판";
        int region_id = 1;

        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .board_name(board_name)
                .board_type(board_type)
                .region_id(region_id)
                .build();

        String url = "http://localhost:" + port + "/api/v1/board";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Board> boardList = boardRepository.findAll();

        for(Board board : boardList) {
            System.out.println(board);
        }
    }

    @Test
    @Transactional
    public void deleteTest() {
        // given
        Long board_id = 1L;

        // when
        boardRepository.deleteById(board_id);

        // then
        Board checkBoard = boardRepository.findById(board_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 board입니다."));
    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        Board savedBoard = boardRepository.findById(5L).orElseThrow(() -> new IllegalArgumentException("해당 Board가 존재하지 않습니다."));

        Long updateId = savedBoard.getBoard_id();
        String expectedName = "시험용 Board";
        String expectedType = "공지";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .board_name(expectedName)
                .board_type(expectedType)
                .region_id(1)
                .build();

        String url = "http://localhost:" + port + "/api/v1/board/" + updateId;

        HttpEntity<BoardUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        // when
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        Board result = boardRepository.getById(updateId);
        System.out.println(result);
    }

    @Test
    public void readTest() {
        List<Board> boardList = boardRepository.findAll();

        for(Board board : boardList) {
            System.out.println(board);
        }
    }
}
