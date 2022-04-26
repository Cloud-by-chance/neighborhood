package com.example.nuvi_demo.domain.member;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        String user_id = "test2";
        String nick_name = "test2";
        String passwd = "test2";
        int age = 30;
        String email = "test2@gmail.com";

        memberRepository.saveAndFlush(Member.builder().user_id(user_id)
                .nick_name(nick_name)
                .email(email)
                .age(age)
//                .passwd(passwd)
                .region_id(1)
                .build());
        // when
        Member savedMember = memberRepository.findById(user_id).get();

        // then
        assertThat(savedMember.getNick_name()).isEqualTo(nick_name);
        assertThat(savedMember.getAge()).isEqualTo(age);
        assertThat(savedMember.getEmail()).isEqualTo(email);
//        assertThat(savedMember.getPasswd()).isEqualTo(passwd);
        assertThat(savedMember.getUser_id()).isEqualTo(user_id);
    }

    @Test
    @Transactional
    public void 회원삭제() {
        // given
        String user_id = "test2";
        Member member;
        boolean isExisted = false;
        // when
        try {
            member = memberRepository.getById(user_id);
            System.out.println(member);
            isExisted = true;
            if(isExisted) {
                memberRepository.deleteById(member.getUser_id());
            }
        } catch(EntityNotFoundException e) {
            System.out.println("test2 user가 존재하지 않습니다.");
        }
    }

    /*@Test
    @Transactional
    public void 회원수정() {
        //given
        String new_user_id = "test2";
        String new_nick_name = "test2";
        String new_passwd = "test2";
        int new_age = 30;
        String new_email = "test2@gmail.com";
        String old_user_id = "test3";
        int old_region_id;
        boolean isExisted = false;

        Member member = memberRepository.getById(old_user_id).orElseThrow(() -> new IllegalArgumentException("해당 user가 존재하지 않습니다."));
        System.out.println(member);
        isExisted = true;
        old_region_id = member.getRegion_id();
        // when
        try {

            if(isExisted) {
                memberRepository.saveAndFlush(Member.builder().user_id(new_user_id)
                        .age(new_age)
                        .passwd(new_passwd)
                        .email(new_email)
                        .nick_name(new_nick_name)
                        .region_id(old_region_id)
                        .build());
            }
        } catch(EntityNotFoundException e) {
            System.out.println("test2 user가 존재하지 않습니다.");
        }

        //then
        Member changedMember = memberRepository.getById(new_user_id);

        System.out.println("==========================================");
        System.out.println(changedMember);
        System.out.println("==========================================");
    }*/
}
