package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
   Optional<Member> findByUserId(String nickname);
   boolean existsByUserId(String nickname);
   boolean existsByPassword(String password);

   boolean existsByEmail(String email);

   boolean existsByNickname(String nickname);
}