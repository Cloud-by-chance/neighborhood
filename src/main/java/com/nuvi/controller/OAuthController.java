package com.nuvi.controller;

import com.nuvi.domain.Member;
import com.nuvi.domain.UserDTO;
import com.nuvi.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/oauth/callback/kakao")
    public void setKakaoUserInfo(@RequestParam String code) {
        String accessToken = oAuthService.getKakaoAccessToken(code);
        oAuthService.getUserInfo(accessToken);
    }
}
