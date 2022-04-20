package com.example.nuvi_demo.web.file;

import com.example.nuvi_demo.domain.file.File;
import com.example.nuvi_demo.domain.file.FileRepository;
import com.example.nuvi_demo.web.dto.file.FileSaveRequestDto;
import com.example.nuvi_demo.web.dto.file.FileUpdateRequestDto;
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
public class FileApiControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FileRepository fileRepository;

/*    @After
    public void cleanup() {
        fileRepository.deleteAll();
    }*/

    @Test
    @Transactional
    public void createTest() {
        // given
        // String name, String org_name, Long byte_size, String save_path, Long post_id, Long board_id
        String name = "Test File";
        String org_name = "Test Org Name";
        Long byte_size = 12345L;
        String save_path = "C:/test/Filetest";
        Long post_id = 8L;
        Long board_id = 5L;

        FileSaveRequestDto requestDto = FileSaveRequestDto.builder()
                .name(name)
                .org_name(org_name)
                .byte_size(byte_size)
                .save_path(save_path)
                .post_id(post_id)
                .board_id(board_id)
                .build();

        String url = "http://localhost:" + port + "/api/v1/file";
        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<File> fileList = fileRepository.findAll();

        for(File file : fileList) {
            System.out.println(file);
        }

    }

    @Test
    @Transactional
    public void deleteTest() {
        Long fileId = 2L;

        fileRepository.deleteById(fileId);
    }

    @Test
    @Transactional
    public void updateTest() {
        // given
        File savedFile = fileRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        // String name, String org_name, Long byte_size, String save_path
        Long updateFileId = savedFile.getId();
        String expectedName = "updatedName";
        String expectedOrgName = "updatedOrg";
        Long expectedByte = 999999L;
        String expectedPath = "D:/hello/world";

        FileUpdateRequestDto requestDto = FileUpdateRequestDto.builder()
                .name(expectedName)
                .org_name(expectedOrgName)
                .byte_size(expectedByte)
                .save_path(expectedPath)
                .build();

        String url = "http://localhost:" + port + "/api/v1/file/" + updateFileId;

        HttpEntity<FileUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        // when
        restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // then
        File result = fileRepository.getById(updateFileId);
        System.out.println("=====================================================");
        System.out.println(result);
        System.out.println("=====================================================");
    }

    @Test
    public void readTest() {
        List<File> fileList = fileRepository.findAll();

        for(File file : fileList) {
            System.out.println(file);
        }
    }
}
