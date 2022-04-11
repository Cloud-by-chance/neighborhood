package com.example.nuvi_demo.service.user;

import com.example.nuvi_demo.Exception.CUserNotFoundException;
import com.example.nuvi_demo.Repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


//토큰에 세팅된 유저 회원 정보를 조회 하는 서비스
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    public UserDetails loadUserByUsername(String userPk) { //UserDetail에서 userPK값으로 유저 정보를 업데이트 한다.
        return userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new); //회원 정보 조회 에러시 NotFound발생
    }
}