package com.nuvi.repository;

import com.nuvi.domain.KakaoDTO;
import com.nuvi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface kakaoRepository extends JpaRepository<KakaoDTO, String> {
    // 정보 저장
}
