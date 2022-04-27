package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.member.MemberService;
import com.example.nuvi_demo.web.dto.member.MemberResponseDto;
import com.example.nuvi_demo.web.dto.member.MemberSaveRequestDto;
import com.example.nuvi_demo.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public String save(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @PutMapping("/api/v1/member/{user_id}")
    public String update(@PathVariable String user_id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(user_id, requestDto);
    }

    @GetMapping("/api/v1/member/{user_id}")
    public MemberResponseDto findById(@PathVariable("user_id") String user_id) {
        return memberService.findById(user_id);
    }
}
