package com.nuvi.service;

import com.nuvi.domain.KakaoDTO;
import com.nuvi.domain.Member;
import com.nuvi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        //return member.getUser_id();
    }

    public void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getNick_name())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String id) {
        return memberRepository.findById(id);
    }

}
