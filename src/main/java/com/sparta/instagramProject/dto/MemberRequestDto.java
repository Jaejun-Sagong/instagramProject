package com.sparta.instagramProject.dto;

import com.sparta.instagramProject.model.Authority;
import com.sparta.instagramProject.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
//
   private String nickname;

   private String email;
   private String password;


   public Member toMember(PasswordEncoder passwordEncoder) {
      return Member.builder()
              .email(email)
              .nickname(nickname)
              .password(passwordEncoder.encode(password))
              .authority(Authority.ROLE_USER)
              .build();
   }

   public UsernamePasswordAuthenticationToken toAuthentication() {
      return new UsernamePasswordAuthenticationToken(email, password);
   }
}

