package com.sparta.instagramProject.service;

import com.sparta.instagramProject.dto.HeartResponseDto;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.Comment;
import com.sparta.instagramProject.model.Heart;
import com.sparta.instagramProject.model.Member;
import com.sparta.instagramProject.repository.ArticleRepository;
import com.sparta.instagramProject.repository.CommentRepository;
import com.sparta.instagramProject.repository.HeartRepository;
import com.sparta.instagramProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    //사용자가 이미 좋아요 한 게시물인지 체크
    public boolean isAlreadyHeartToArticle(String nickname, Article article) {
        return heartRepository.findByNicknameAndArticle(nickname, article).isPresent();
    }//사용자가 이미 좋아요 한 댓글인지 체크

//    private boolean isAlreadyHeartToComment(String nickname, Comment comment) {
//        return heartRepository.findByNicknameAndComment(nickname, comment).isPresent();
//    }

    public String getNickname() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //게시글 좋아요
    @Transactional
    public HeartResponseDto addHeartToArticle(Long articleId) {
        String nickname = getNickname();
        HeartResponseDto heartResponseDto = new HeartResponseDto();
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        //이미 좋아요 누른 게시글이라면
        if (isAlreadyHeartToArticle(nickname, article)) {
            Heart heart = heartRepository.findByNicknameAndArticle(nickname, article).get();
            article.deleteHeart(heart);
            article.setIsLike(false);
            heartRepository.delete(heart);
            heartResponseDto.setLike(false);
        } else {
            Heart heart = Heart.builder()
                    .article(article)
                    .nickname(nickname)
                    .build();
            article.setIsLike(true);
            heartResponseDto.setLike(true);
            heartRepository.save(heart);
        }
        heartResponseDto.setHeartCnt(heartRepository.countByArticle(article));
        heartResponseDto.setArticleId(articleId);

        return heartResponseDto;
    }


    //댓글 좋아요
//    @Transactional
//    public Long addHeartToComment(Long commentId) {
//        String nickname = getNickname();
//
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 댓글 존재하지 않습니다."));
//
//        if (isAlreadyHeartToComment(nickname, comment)) {
//            Heart heart = heartRepository.findByNicknameAndComment(nickname, comment).get();
//            comment.deleteHeart(heart);
//            heartRepository.delete(heart);
//        } else {
//            Heart heart = new Heart(nickname, comment);
//
//            comment.addHeart(heart);
//            heartRepository.save(heart);
//        }
//        return heartRepository.countByComment(comment);
//    }

//    public Long addHeartToComment(Long articleId, Long commentId) {
//
//    }
}