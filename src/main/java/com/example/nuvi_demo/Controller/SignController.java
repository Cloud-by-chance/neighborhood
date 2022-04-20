package com.example.nuvi_demo.Controller;


import com.example.nuvi_demo.Entity.Auth;
import com.example.nuvi_demo.Entity.TokenVo;
import com.example.nuvi_demo.Entity.User;
import com.example.nuvi_demo.Entity.UserVo;
import com.example.nuvi_demo.Exception.CEmailSigninFailedException;
import com.example.nuvi_demo.Repo.AuthRepo;
import com.example.nuvi_demo.Repo.UserJpaRepo;
import com.example.nuvi_demo.config.Security.JwtTokenProvider;
import com.example.nuvi_demo.domain.token.Token;
import com.example.nuvi_demo.model.response.*;
import com.example.nuvi_demo.service.user.KakaoAPIService;
import com.example.nuvi_demo.service.user.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@RestController
@Slf4j
@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RequestMapping(value = "/v1", produces = "application/json;charset=utf-8")
@CrossOrigin(origins = "*") //리액트와 연동하기 위한 CROS 설정
public class SignController { //가입과 로그인에 대한 COntroller이다.
    private final KakaoAPIService kakao;
    @Autowired
    private final UserJpaRepo userJpaRepo;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final AuthRepo authRepo;
    @Autowired //로그인 안되던 이유..
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원 로그인 Token 발급", required = true) @RequestBody UserVo userVo) {
        User user_check = userJpaRepo.findById(userVo.getId()).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(userVo.getPassword(),user_check.getPassword() )) //저장된 password와 받아온 password 비교
            throw new CEmailSigninFailedException();

        //토큰 2개를 return하기 위한 List return
        ArrayList<String> jwt =new ArrayList<String>();
        String refresh_token = jwtTokenProvider.createRefreshToken();
        String access_token= jwtTokenProvider.createToken(String.valueOf(user_check.getUser_id()), user_check.getRoles());

        jwt.add(refresh_token);
        jwt.add(access_token);
        //Auth 정보 저장을 위해 토큰 값과 userID를 가져와 builder해준다.
        authRepo.save(Auth.builder().Refresh_token(refresh_token)
                .Access_token(access_token)
                .user_id(userVo.getId())
                .build());
//        authRepo.getById("Refresh_token");
        //jwt에 Access 토큰과 refresh 토큰을 넣고 Tostring으로 보내준다.
        return responseService.getSingleResult(jwt.toString());
//        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user_check.getUser_id()), user_check.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "Api 요청 내용", required = true) @RequestBody User user2) {

        userJpaRepo.save(User.builder()
                .user_id(user2.getUser_id())
                .password(passwordEncoder.encode(user2.getPassword()))
                .nick_name(user2.getNick_name())
                .email(user2.getEmail())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }
    //Refresh 확인용
    @Transactional
    @PostMapping(value = "/refreshtoken")
    public SingleResult<String> refreshtoken(String token) { //에러가 뜬다면 refresh 토큰을 받음 해당 토큰으로 재 발급 받는다.
        String userid = authRepo.findById(token).get().getUser_id();
        String access_token = jwtTokenProvider.createToken(String.valueOf(userid), userJpaRepo.findById(userid).get().getRoles());
        log.info(jwtTokenProvider.informationToken(token).toString());
        log.info(authRepo.findById(token).toString());
        if (jwtTokenProvider.validateToken(token)) { //만약 refresh 토큰의 기간이 유효하면 새 access 토큰을 발행
            authRepo.save(Auth.builder().Refresh_token(token).Access_token(access_token).user_id(userid).build());
        } else {
            authRepo.deleteById(token); // 유효기간이 지난 토큰을 삭제 후 다시 생성
            String refresh_token = jwtTokenProvider.createRefreshToken();
            authRepo.save(Auth.builder().Refresh_token(refresh_token).Access_token(access_token).user_id(userid).build());
        }
        return responseService.getSingleResult(access_token); //새로 생성된 access_token 발행
    }

    @RequestMapping(value = "/kakaoLogin")
    public SingleResult<String> login(@RequestBody TokenVo tokenVo, HttpSession session){
        HashMap<String, Object> userInfo = kakao.getUserInfo(tokenVo.getAccessToken());
        User user_check = userJpaRepo.findById(userInfo.get("id").toString()).orElseThrow(CEmailSigninFailedException::new);
        String jwtToken = jwtTokenProvider.createToken(String.valueOf(user_check.getUser_id()), user_check.getRoles());
        if (tokenVo.getJwt() == null) {
            tokenVo.setJwt(jwtToken);
        }
        Token token = new Token(userInfo.get("id").toString(), tokenVo);
        kakao.saveToken(token);

        return responseService.getSingleResult(jwtToken);
    }

}