package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom{

    List<Article> findAllByNickname(String nickname);

    List<Article> findByIsLikeTrue();
}
