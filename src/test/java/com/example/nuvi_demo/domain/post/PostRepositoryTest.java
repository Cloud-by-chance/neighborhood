package com.example.nuvi_demo.domain.post;

import com.example.nuvi_demo.web.dto.post.PostSaveRequestDto;
import com.example.nuvi_demo.web.dto.post.PostUpdateRequestDto;
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
public class PostRepositoryTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostRepository postRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createTest() {
        // given
        String post_name = "Test Post";
        String content = "Posting test number one.";
        String user_id = "test2";
        Long board_id = 1L;

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .post_name(post_name)
                .content(content)
                .user_id(user_id)
                .board_id(board_id)
                .build();

        String url = "http://localhost:" + port + "/api/v1/post";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> postList = postRepository.findAll();

        for(Post post : postList) {
            System.out.println(post);
        }
    }

    @Test
    @Transactional
    public void deleteTest() {
        Long post_id = 3L;
//        Post deletePost = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));

//        postRepository.delete(deletePost);
        postRepository.deleteById(post_id);
    }

    @Test
    public void updateTest() {
        // given
        Post savedPost = postRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        /*Post savedPost = postRepository.save(Post.builder()
                .post_name("Test Post")
                .content("Test Posting")
                .board_id(1L)
                .user_id("test2")
                .build());*/

        Long updateId = savedPost.getPost_id();
        String expectedTitle = "Test Post";
        String expectedContent = "Test Posting";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder().post_name(expectedTitle)
                .content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/post/" + updateId;

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        // when
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        List<Post> postList = postRepository.findAll();

        for(Post post : postList) {
            System.out.println(post);
        }
    }

    @Test
    public void readTest() {
        // given
        // when
        List<Post> postList = postRepository.findAll();

        // then
        for(Post post : postList) {
            System.out.println(post);
        }
    }
}
