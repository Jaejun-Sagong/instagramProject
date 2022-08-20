package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
   Optional<Member> findByNickname(String nickname);

   boolean existsByEmail(String email);

   boolean existsByNickname(String nickname);

   Optional<Member> findByEmail(String email);


}