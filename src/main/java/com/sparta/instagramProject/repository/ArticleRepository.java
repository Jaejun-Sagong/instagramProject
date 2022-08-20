package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
