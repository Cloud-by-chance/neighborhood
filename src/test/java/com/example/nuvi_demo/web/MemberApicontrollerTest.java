package com.example.nuvi_demo.web;

import com.example.nuvi_demo.domain.member.Member;
import com.example.nuvi_demo.domain.member.MemberRepository;
import com.example.nuvi_demo.web.dto.member.MemberSaveRequestDto;
import com.example.nuvi_demo.web.dto.member.MemberUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Commit
public class MemberApicontrollerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

   /* @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }*/
/*
    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }*/

    @Test
//    @WithMockUser(roles="USER")
    public void createMember() throws Exception {
        // given
        String user_id = "test4";
        String nick_name = "test4";
        String email = "test4@gmail.com";
        String passwd = "test4";
        int age = 33;
        int region_id = 1;

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder().user_id(user_id)
                .nick_name(nick_name)
                .email(email)
                .passwd(passwd)
                .age(age)
                .region_id(region_id)
                .build();

        String url = "http://localhost:" + port + "/api/v1/member";

        // when
/*        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());*/
        /*Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("KEY", "VALUE");

        HttpEntity<String> entity = new HttpEntity<>("", headers);*/

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getUser_id()).isEqualTo(user_id);
        assertThat(memberList.get(0).getNick_name()).isEqualTo(nick_name);
    }

    @Test
    public void modify() throws Exception {
        // given
        Member savedMember = memberRepository.save(Member.builder()
                .user_id("test5")
                .nick_name("test5")
                .email("test5@gmail.com")
                .age(35)
                .region_id(1)
//                .passwd("test5")
                .build());

        String updatedId = savedMember.getUser_id();
        String expectedNickName = "test5";
        String expectedEmail = "test5@gmail.com";

        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder().user_id(updatedId)
                .nick_name(expectedNickName)
                .email(expectedEmail)
                .build();

        String url = "http://localhost:" + port + "/api/v1/member/" + updatedId;

        HttpEntity<MemberUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Member> memberList = memberRepository.findAll();

        for(Member member : memberList) {
            System.out.println(member);
        }
    }
}
