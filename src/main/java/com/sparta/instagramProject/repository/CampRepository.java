package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampRepository extends JpaRepository<Article, Long> {

}
