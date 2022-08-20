package com.sparta.instagramProject.service;

import com.sparta.instagramProject.dto.MemberRequestDto;
import com.sparta.instagramProject.dto.MemberResponseDto;
import com.sparta.instagramProject.dto.TokenDto;
import com.sparta.instagramProject.dto.TokenRequestDto;
import com.sparta.instagramProject.jwt.TokenProvider;
import com.sparta.instagramProject.model.Member;
import com.sparta.instagramProject.model.RefreshToken;
import com.sparta.instagramProject.repository.MemberRepository;
import com.sparta.instagramProject.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if(!(Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",memberRequestDto.getEmail()))){
            throw new IllegalArgumentException("이메일 조건을 확인해주세요.");
        }
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())){
            throw new IllegalArgumentException("동일한 이메일이 존재합니다.");
        }
        if(!(Pattern.matches("[a-zA-Z0-9]*$",memberRequestDto.getNickname()) && (memberRequestDto.getNickname().length() > 3 && memberRequestDto.getNickname().length() <13))){
            throw new IllegalArgumentException("닉네임 조건을 확인해주세요.");
        }
        if (memberRepository.existsByNickname(memberRequestDto.getNickname())){
            throw new IllegalArgumentException("동일한 닉네임이 존재합니다.");
        }
        if(!(Pattern.matches("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{3,16}$",memberRequestDto.getPassword()))){
            throw new IllegalArgumentException("비밀번호 조건을 확인해주세요.");
        }

//        if (!memberRequestDto.getPassword().equals(memberRequestDto.getPasswordConfirm()))
//            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }


    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        if(!memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent())
            throw new IllegalArgumentException("Email이 일치하지 않습니다.");
//        if (!memberRepository.existsByEmail(memberRequestDto.getEmail()) ||
//                !memberRepository.existsByPassword(passwordEncoder.encode(memberRequestDto.getPassword()))) {
//            throw new RuntimeException("사용자를 찾을 수 없습니다");
//        }
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        try{
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        

        // 5. 토큰 발급
        return tokenDto;
        } catch (Exception e){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKkey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getVvalue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
//
//    public Boolean idCheck(MemberRequestDto memberRequestDto) {
//        if (memberRepository.existsByUserId(memberRequestDto.getNickname()))
//        {
//            return false;
//        }
//        return true;
//    }
//    public void logout(String accessToken, String refreshToken) {
//        redisUtil.setBlackList(accessToken, "accessToken", 1800);
//        redisUtil.setBlackList(refreshToken, "refreshToken", 60400);
//    }
}