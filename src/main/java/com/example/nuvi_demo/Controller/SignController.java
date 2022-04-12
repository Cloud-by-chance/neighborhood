package com.example.nuvi_demo.Controller;


import com.example.nuvi_demo.Entity.User;
import com.example.nuvi_demo.Entity.UserVo;
import com.example.nuvi_demo.Exception.CEmailSigninFailedException;
import com.example.nuvi_demo.Repo.UserJpaRepo;
import com.example.nuvi_demo.config.Security.JwtTokenProvider;
import com.example.nuvi_demo.model.response.*;
import com.example.nuvi_demo.service.user.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1",produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*") //리액트와 연동하기 위한 CROS 설정
public class SignController { //가입과 로그인에 대한 COntroller이다.
    @Autowired
    private final UserJpaRepo userJpaRepo;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final ResponseService responseService;

    @Autowired //로그인 안되던 이유..
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원 로그인 Token 발급", required = true) @RequestBody UserVo userVo) {
//    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
//                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
//        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
//        if (!passwordEncoder.matches(password, user.getPassword()))
//            throw new CEmailSigninFailedException();
//
//        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
        log.info(userVo.toString());
        User user_check = userJpaRepo.findByUid(userVo.getId()).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(userVo.getPassword(),user_check.getPassword() )) //저장된 password와 받아온 password 비교
            throw new CEmailSigninFailedException();


        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user_check.getMsrl()), user_check.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "Api 요청 내용", required = true) @RequestBody User user2) {
//    public CommonResult signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
//                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
//                               @ApiParam(value = "이름", required = true) @RequestParam String name) {

//        userJpaRepo.save(User.builder()
//                .uid(id)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .roles(Collections.singletonList("ROLE_USER"))
//                .build());
        userJpaRepo.save(User.builder()
                .uid(user2.getUid())
                .password(passwordEncoder.encode(user2.getPassword()))
                .name(user2.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }
}