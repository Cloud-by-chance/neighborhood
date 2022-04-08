package com.nuvi.repository;

import com.nuvi.domain.Member;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원조회() {
        List<Member> memberList = memberRepository.findAll();
        System.out.println("==================================================");
        for(Member member : memberList) {
            System.out.println(member);
        }
        System.out.println("==================================================");
    }

    @Test
    @Transactional
    public void 회원가입() {
        // given
        String user_id = "test3";
        String nick_name = "test3";
        String passwd = "test3";
        int age = 30;
        String email = "test2@gmail.com";

        memberRepository.saveAndFlush(Member.builder().user_id(user_id)
                .nick_name(nick_name)
                .email(email)
                .age(age)
                .passwd(passwd)
                .region_id(1)
                .build());
        // when
        Member savedMember = memberRepository.findById(user_id).get();

        // then
        assertThat(savedMember.getNick_name()).isEqualTo(nick_name);
        assertThat(savedMember.getAge()).isEqualTo(age);
        assertThat(savedMember.getEmail()).isEqualTo(email);
        assertThat(savedMember.getPasswd()).isEqualTo(passwd);
        assertThat(savedMember.getUser_id()).isEqualTo(user_id);
    }
}
