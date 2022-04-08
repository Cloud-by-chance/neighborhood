package com.nuvi.controller;

import com.nuvi.domain.KakaoDTO;
import com.nuvi.domain.Member;
import com.nuvi.service.MemberService;
import com.nuvi.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private MemberService memberService;


    @GetMapping("/oauth/callback/kakao")
    @ResponseBody
    public void kakaoLogin(String code) {
        String accessToken = oAuthService.getKakaoAccessToken(code);
        KakaoDTO userInfo = oAuthService.getUserInfo(accessToken);
        System.out.println("###access_Token#### : " + accessToken);
        System.out.println("###id#### : " + userInfo.getK_id());
        System.out.println("###nickname#### : " + userInfo.getK_name());
    }

  /*  @GetMapping("/OAuth")
    public String callbackUserInfo() {
        return userInfo;
    }*/
}
