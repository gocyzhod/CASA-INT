package kr.co.casa_int.controller;

import kr.co.casa_int.entity.User;
import kr.co.casa_int.service.UserMgService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author gyutae park
 * @since 2023.04.10
 * @apiNote /user/no/** : 비회원 && /user/** : 회원
 */

@RestController
@RequestMapping( value = "/user")
// 임시로 다 뚫어둠
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserMgController {

    private final UserMgService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping(value ={"/test"})
    public String userTestApi(){
        return "Hello /user/test Casa! ";
    }

    // 아이디 생성 API
    // API_USER_001
    @PostMapping(value = {"/no/singUp"})
    public ResponseEntity<String> singUp(@RequestBody User userInfo) throws Exception{

        logger.info("RequestBody=[{}]\n", userInfo);

        String response = service.singUp(userInfo);

        logger.info("response=[{}]\n", response);

        // 무언가가 중복될 경우 : 닉네임, 이메일
        if ( response.contains("duplicate") ){
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // 아이디 생성 성공
        else if (response.contains("Success")){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // 무언가의 이유로 실패
        else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API_USER_002
    @PutMapping(value = {"/updateUser"})
    public ResponseEntity<String> updateUser(@RequestBody User userInfo, @AuthenticationPrincipal User loginUser) throws Exception{
        // 참고
        // https://u-it.tistory.com/entry/BCrypt-Spring-security-%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%95%94%ED%98%B8%ED%99%94-%EB%B3%B5%ED%98%B8%ED%99%94-%EB%A1%9C%EC%A7%81-%ED%99%9C%EC%9A%A9

        logger.info("RequestBody=[{}]\n", userInfo);
        logger.info("loginUser=[{}]\n", loginUser.getUserNickname());

        String response = service.updateUser(userInfo,loginUser);



        return null;
    }

}
