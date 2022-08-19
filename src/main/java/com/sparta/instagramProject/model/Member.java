package com.sparta.instagramProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@Entity
@Builder
public class Member  {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.IDENTITY : ID값이 서로 영향없이 자기만의 테이블 기준으로 올라간다.
   private Long id;

   @Column(nullable = false, unique = true)
   private String nickname;
   @JsonIgnore
   @Column(nullable = false)
   private String password;

   @Enumerated(EnumType.STRING)
   private Authority authority;


}
