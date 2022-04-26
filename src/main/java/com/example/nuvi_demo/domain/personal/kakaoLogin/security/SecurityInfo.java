package com.example.nuvi_demo.domain.personal.kakaoLogin.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Component
public class SecurityInfo {
    private String CLIENT_ID = "b9cde1037a96070da43fd0e36b35b9de"; // TODO Rest API Key
    private String redirect_URL = "http://localhost:3000/v1/kakao"; //TODO 인가코드 받은 redirect_uri 입력
}