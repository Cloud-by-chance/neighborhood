
package com.example.nuvi_demo.service.user;

import com.example.nuvi_demo.Entity.Token;
import com.example.nuvi_demo.Entity.User;
import com.example.nuvi_demo.Repo.UserJpaRepo;
import com.example.nuvi_demo.domain.member.Member;
import com.example.nuvi_demo.domain.member.MemberRepository;
import com.example.nuvi_demo.domain.member.TokenRepository;
import com.example.nuvi_demo.personal.kakaoLogin.security.SecurityInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAPIService {
    @Autowired
    private MemberRepository mr;
    @Autowired
    private final SecurityInfo securityInfo;
    @Autowired
    private  final TokenRepository tokenRepository;
    @Autowired
    private final UserJpaRepo userJpaRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    public Optional<String> getKakaoAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + securityInfo.getCLIENT_ID()); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=" + securityInfo.getRedirect_URL()); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            //int responseCode = conn.getResponseCode();
            //System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.of(accessToken);
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("UserInfo - response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            long id = element.getAsJsonObject().get("id").getAsLong();
            String nickName = properties.getAsJsonObject().get("nickname").getAsString();
            //String age = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("age_range").getAsString();

            userInfo.put("id", id);
            userInfo.put("nickName", nickName);
            //userInfo.put("age", age);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        Optional<Member> findMember = mr.findById(userInfo.get("id").toString());

        if (findMember.isEmpty()) {
            mr.saveAndFlush(Member.builder().user_id(userInfo.get("id").toString())
                    .nick_name(userInfo.get("nickName").toString())
                    .password("sdf")
                    .region_id(1)  //TODO 지역코드 추후 변경
                    .build());

            userJpaRepo.save(User.builder()
                    .user_id(userInfo.get("id").toString())
                    .password(passwordEncoder.encode(userInfo.get("id").toString() + accessToken))
                    .nick_name(userInfo.get("nickName").toString())
                    //.email(userInfo.getEmail())
//                .age(user2.getAge())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        }
        return userInfo;
    }

    // 로그아웃
    public void kakaoLogout(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

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

    public void saveToken(String id, String jwtToken, String refreshToken) {
        tokenRepository.save(Token.builder()
                        .idx(Base64.getEncoder().encodeToString((id+jwtToken).getBytes(StandardCharsets.UTF_8)))
                        .jwt(jwtToken)
                        .refresh_tk(refreshToken)
                        .user_id(id)
                        .build());
    }
}

