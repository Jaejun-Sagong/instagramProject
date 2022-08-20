package com.sparta.instagramProject.repository;


import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByArticle(Article article);
}