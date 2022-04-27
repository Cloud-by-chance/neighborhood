package com.example.nuvi_demo.Controller;



import com.example.nuvi_demo.domain.Entity.*;

import com.example.nuvi_demo.Exception.CEmailSigninFailedException;
//import com.example.nuvi_demo.Repo.AuthRepo;
import com.example.nuvi_demo.Repo.*;
import com.example.nuvi_demo.config.Security.JwtTokenProvider;
import com.example.nuvi_demo.domain.token.Token;
import com.example.nuvi_demo.model.response.*;
import com.example.nuvi_demo.service.user.KakaoAPIService;
import com.example.nuvi_demo.service.user.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
@RequestMapping(value = "/auth", produces = "application/json;charset=utf-8")
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

    @Autowired
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/login")
    public SingleResult<String> signin(@ApiParam(value = "회원 로그인 Token 발급", required = true) @RequestBody UserVo userVo) {
//    public ListResult<String> signin(@ApiParam(value = "회원 로그인 Token 발급", required = true) @RequestBody UserVo userVo) {
        User userCheck = userJpaRepo.findById(userVo.getId()).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(userVo.getPassword(),userCheck.getPassword() )) //저장된 password와 받아온 password 비교
            throw new CEmailSigninFailedException();

        //토큰 2개를 return하기 위한 List return
        ArrayList<String> jwt =new ArrayList<String>();
        String refresh_token = jwtTokenProvider.createRefreshToken(String.valueOf(userCheck.getUser_id()));
        String access_token= jwtTokenProvider.createToken(String.valueOf(userCheck.getUser_id()), userCheck.getRoles());

        jwt.add(refresh_token);
        jwt.add(access_token);
        log.info(refresh_token);
        log.info(userVo.getId());
        //refresh 토큰을 Redis 저장소로 저장한다.
        refreshTokenRedisRepository.save(
                RefreshToken.createRefreshToken(
                        userVo.getId(),
                        refresh_token,
                        LoginCategory.LOCAL,
                        60 * 120 *1000L) //2번째 내용이 n분을 뜻함
        );
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

    //Access 토큰이 잘 오기만 하면 된다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token을 넣으세요", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Token 유효 검사", notes = "Validaiton!")
    @GetMapping(value = "/validate")
    public CommonResult checkToken(String key){
        log.info(key);//

        return responseService.getSuccessResult(); //잘 들어오기만 하면 된다. 왜냐면 이미 들어오는거 자체가 Filter를 거치기 때문.
    }



    @ApiOperation(value = "Refresh Token 인증")
    @PostMapping(value = "/refreshtoken")
    public CommonResult  refreshProcess(@RequestBody String key){
        log.info("키값? : "+ key);
        String userid = jwtTokenProvider.informationToken(key).toString(); //refresh 토큰에 있는 유저 정보를 가져옴
        User userCheck = userJpaRepo.findById(userid).orElseThrow(CEmailSigninFailedException::new); //해당 토큰의 유저가 DB에 있는지
        log.info("유저 ? : "+ userid);


        if (refreshTokenRedisRepository.findById(userid).toString().equals("Optional.empty")) //empty면 Redis에 User관련 토큰이 x
        {
            log.info("Refresh 토큰이 만료되었습니다. Refrsh/Access를 재발급 합니다.");
            //noinspection OptionalGetWithoutIsPresent
            LoginCategory loginCategory = refreshTokenRedisRepository.findById(userid).get().getLoginCategory();

            //토큰 2개 재생성
            ArrayList<String> jwt =new ArrayList<String>();
            String refresh_token = jwtTokenProvider.createRefreshToken(String.valueOf(userCheck.getUser_id()));
            String access_token= jwtTokenProvider.createToken(String.valueOf(userCheck.getUser_id()), userCheck.getRoles());

            jwt.add(refresh_token);
            jwt.add(access_token);


            //Redis에 Refresh token 저장.
            refreshTokenRedisRepository.save(
                    RefreshToken.createRefreshToken(
                            userid,
                            refresh_token,
                            loginCategory,
                            60 * 3* 1000L) //1분임
            );
            return responseService.getSingleResult(jwt.toString());
        }
        else{
            String access_token= jwtTokenProvider.createToken(String.valueOf(userCheck.getUser_id()), userCheck.getRoles());
            log.info("Refresh 확인 Access_Token 재 발급");
            return responseService.getSingleResult(access_token);
        }


    }
    @ApiOperation(value = "로그아웃", notes = "회원을 삭제한다.")
    @PostMapping("/logout")
    public SingleResult<String> logout(@RequestBody TokenVo token) {
        log.info(String.valueOf(token));

        String key = token.getRefreshToken();
        log.info(key);

        String accessToken = token.getAccessToken();

        String userid = jwtTokenProvider.informationToken(key).toString(); //유저 이름을 받아옴
        log.info("로그아웃 하려는 유저 ? : "+ userid);

        //noinspection OptionalGetWithoutIsPresent
        LoginCategory loginCategory = refreshTokenRedisRepository.findById(userid).get().getLoginCategory();

        if (loginCategory == LoginCategory.KAKAO) {
            String reqURL = "https://kapi.kakao.com/v1/user/logout";

            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);

                int responseCode = conn.getResponseCode();
                System.out.println("responseCode : " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        refreshTokenRedisRepository.deleteById(userid);  //유저 이름을 기준으로 Refresh 토큰 삭제

        return responseService.getSingleResult("LogOut이 완료되었습니다");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원번호(user_id)로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{user_id}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable String user_id) {
        userJpaRepo.deleteById(user_id);

        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }

    @RequestMapping(value = "/kakaoLogin")
    public ListResult<String> login(@RequestBody TokenVo tokenVo){
        HashMap<String, Object> userInfo = kakao.getUserInfo(tokenVo.getAccessToken());
        User userCheck = userJpaRepo.findById(userInfo.get("id").toString()).orElseThrow(CEmailSigninFailedException::new);


        String jwtToken = jwtTokenProvider.createToken(String.valueOf(userCheck.getUser_id()), userCheck.getRoles());
        if (tokenVo.getJwt() == null) {
            tokenVo.setJwt(jwtToken);
        }
        String refresh_token = jwtTokenProvider.createRefreshToken(String.valueOf(userCheck.getUser_id()));

        Token token = new Token(userInfo.get("id").toString(), tokenVo);
        //refresh 토큰을 Redis 저장소로 저장한다.
        refreshTokenRedisRepository.save(
                RefreshToken.createRefreshToken(
                        userCheck.getUser_id(),
                        refresh_token,
                        token.getRefresh_token(),
                        LoginCategory.LOCAL,
                        60 * 120 *1000L)
        );

        List<String> res = new ArrayList<>();
        res.add(jwtToken);
        res.add(userInfo.get("nickName").toString());
        res.add(userInfo.get("id").toString());
        res.add(userInfo.get("email").toString());
        res.add(userInfo.get("image").toString());
        res.add(refresh_token);

        return responseService.getListResult(res);
    }

}