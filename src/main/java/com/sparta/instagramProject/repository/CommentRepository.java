package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
    List<Comment> findAllByCamp(Article article);

    List<Comment> findAllByMemberName(String nickname);

}