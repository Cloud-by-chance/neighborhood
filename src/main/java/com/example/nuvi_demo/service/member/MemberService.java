package com.example.nuvi_demo.service.member;

import com.example.nuvi_demo.domain.member.Member;
import com.example.nuvi_demo.domain.member.MemberRepository;
import com.example.nuvi_demo.web.dto.member.MemberResponseDto;
import com.example.nuvi_demo.web.dto.member.MemberSaveRequestDto;
import com.example.nuvi_demo.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor  // final이 선언된 모든 필드를 인자값으로 하는 생성자
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public String save(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getUser_id();
    }

    @Transactional
    public String update(String user_id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다."));

        // String user_id, String nick_name, String passwd, String email, int age, int region_id
        member.update(requestDto.getUser_id(), requestDto.getNick_name(), requestDto.getEmail());
        return user_id;
    }

    public MemberResponseDto findById(String user_id) {
        Member entity = memberRepository.findById(user_id).orElseThrow(() ->
            new IllegalArgumentException("해당 사용자는 존재하지 않습니다."));

        return new MemberResponseDto(entity);
    }
}
