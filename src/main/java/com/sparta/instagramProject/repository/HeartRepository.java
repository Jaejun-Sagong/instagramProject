package com.sparta.instagramProject.repository;


import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.Comment;
import com.sparta.instagramProject.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {


    Optional<Heart> findByNicknameAndArticle(String nickname, Article article);

    Long countByArticle(Article article);

    boolean existsByNicknameAndArticle(String nickname, Article article);
    List<Heart> findAllByNickname(String nickname);
}