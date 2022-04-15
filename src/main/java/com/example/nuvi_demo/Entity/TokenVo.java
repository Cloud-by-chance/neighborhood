package com.example.nuvi_demo.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenVo {

    private String accessToken;

    private String JWT;

    private String refreshToken;
}
