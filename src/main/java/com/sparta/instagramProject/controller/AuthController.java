package com.sparta.instagramProject.controller;

import com.sparta.instagramProject.dto.MemberRequestDto;
import com.sparta.instagramProject.dto.MemberResponseDto;
import com.sparta.instagramProject.dto.TokenDto;
import com.sparta.instagramProject.dto.TokenRequestDto;
import com.sparta.instagramProject.model.Member;
import com.sparta.instagramProject.repository.MemberRepository;
import com.sparta.instagramProject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*", exposedHeaders = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberRepository memberRepository;



    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse response) {
        TokenDto tokenDto = authService.login(memberRequestDto);
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.setHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.setHeader("Access-Token-Expire-Time", String.valueOf(tokenDto.getAccessTokenExpiresIn()));

        return ResponseEntity.ok(tokenDto.getNickname());

    }

//    @PostMapping("/idCheck")
//    public Boolean idCheck(@RequestBody MemberRequestDto memberRequestDto) {
//        return authService.idCheck(memberRequestDto);
//    }
    @PostMapping("/reissue")  //재발급을 위한 로직
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
//    @DeleteMapping("/authenticate")
//    public HttpStatus logout(
//            @RequestBody @Valid TokenDto requestTokenDto) {
//        authService.logout(requestTokenDto.getAccessToken(), requestTokenDto.getRefreshToken());
//        return OK;
//    }
}