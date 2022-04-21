package com.example.nuvi_demo.Controller;


//<<<<<<< HEAD
import com.example.nuvi_demo.domain.Entity.User;
import com.example.nuvi_demo.domain.Entity.UserVo;
import com.example.nuvi_demo.domain.Entity.TokenVo;
//=======
import com.example.nuvi_demo.domain.Entity.Auth;
//>>>>>>> 4e5c6e3868e5b758f3fb8c5524967f065b31d093
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


import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
//    public ListResult<String> signin(@ApiParam(value = "회원 로그인 Token 발급", required = true) @RequestBody UserVo userVo) {
        User user_check = userJpaRepo.findById(userVo.getId()).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(userVo.getPassword(),user_check.getPassword() )) //저장된 password와 받아온 password 비교
            throw new CEmailSigninFailedException();

        //토큰 2개를 return하기 위한 List return
        ArrayList<String> jwt =new ArrayList<String>();
        String refresh_token = jwtTokenProvider.createRefreshToken(String.valueOf(user_check.getUser_id()));
        String access_token= jwtTokenProvider.createToken(String.valueOf(user_check.getUser_id()), user_check.getRoles());

        jwt.add(refresh_token);
        jwt.add(access_token);
        //Auth 정보 저장을 위해 토큰 값과 userID를 가져와 builder해준다.

        authRepo.save(Auth.builder().Refresh_token(refresh_token)
                .idx(Base64.getEncoder().encodeToString((userVo.getId()+access_token).getBytes(StandardCharsets.UTF_8)))
                .Access_token(access_token)
                .user_id(userVo.getId())
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build());
//        authRepo.getById("Refresh_token");
        //jwt에 Access 토큰과 refresh 토큰을 넣고 Tostring으로 보내준다.
        return responseService.getSingleResult(jwt.toString());
//        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user_check.getUser_id()), user_check.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "Api 요청 내용", required = true) @RequestBody User user2) {
//    public CommonResult signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
//                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
//                               @ApiParam(value = "이름", required = true) @RequestParam String name) {


        userJpaRepo.save(User.builder()
                .user_id(user2.getUser_id())
                .password(passwordEncoder.encode(user2.getPassword()))
                .nick_name(user2.getNick_name())
                .email(user2.getEmail())
//                .age(user2.getAge())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }

    @Transactional
    @PostMapping(value = "/refreshtoken")
    public SingleResult<String> refreshtoken(String token) { //에러가 뜬다면 refresh 토큰을 받음 해당 토큰으로 재 발급 받는다.

//        String userid = authRepo.findById(token).get().getUser_id();
        String userid = jwtTokenProvider.informationToken(token).toString(); //refresh 토큰에 있는 유저 정보를 가져옴
//        log.info("유저 ? : "+ userid);
//        log.info("유저 역할? : "+ userJpaRepo.findById(userid).toString());

        String access_token = jwtTokenProvider.createToken(String.valueOf(userid), userJpaRepo.findById(userid).get().getRoles());
//        log.info("유저 정보? : " + jwtTokenProvider.informationToken(token));

        if (jwtTokenProvider.validateToken(token)) { //만약 refresh 토큰의 기간이 유효하면 새 access 토큰을 발행
//            authRepo.save(Auth.builder().Refresh_token(token).Access_token(access_token).user_id(userid).build());
            authRepo.save(Auth.builder().Refresh_token(token)
                    .idx(Base64.getEncoder().encodeToString((userid + access_token).getBytes(StandardCharsets.UTF_8)))
                    .Access_token(access_token)
                    .user_id(userid)
//                    .timestamp(Timestamp.valueOf(LocalDateTime.now())) //timestamp는 업데이트 되지 않는다.
                    .build());
        } else {
            authRepo.deleteById(userid); // 유효기간이 지난 토큰을 삭제 후 다시 생성
            String refresh_token = jwtTokenProvider.createRefreshToken(userid);
            authRepo.save(Auth.builder().Refresh_token(token)
                    .idx(Base64.getEncoder().encodeToString((userid + access_token).getBytes(StandardCharsets.UTF_8)))
                    .Access_token(access_token)
                    .user_id(userid)
                    .timestamp(Timestamp.valueOf(LocalDateTime.now())) //삭제후 생성이므로 timestamp도 재설정
                    .build());
        }
        return responseService.getSingleResult(access_token);
    }

    @RequestMapping(value = "/kakaoLogin")
    public ListResult<String> login(@RequestBody TokenVo tokenVo){
        HashMap<String, Object> userInfo = kakao.getUserInfo(tokenVo.getAccessToken());
        User user_check = userJpaRepo.findById(userInfo.get("id").toString()).orElseThrow(CEmailSigninFailedException::new);

        String jwtToken = jwtTokenProvider.createToken(String.valueOf(user_check.getUser_id()), user_check.getRoles());
        if (tokenVo.getJwt() == null) {
            tokenVo.setJwt(jwtToken);
        }

        Token token = new Token(userInfo.get("id").toString(), tokenVo);
        kakao.saveToken(token);

        List<String> res = new ArrayList<>();
        res.add(jwtToken);
        res.add(userInfo.get("nickName").toString());

        return responseService.getListResult(res);
    }

}