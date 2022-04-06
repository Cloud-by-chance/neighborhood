package com.nuvi.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @GetMapping("/oauth/callback/kakao")
    public @ResponseBody
    String kakaoCallback(String code) {
        return "카카오 인증완료" + code;
    }
}
