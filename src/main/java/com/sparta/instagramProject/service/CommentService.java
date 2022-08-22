package com.sparta.instagramProject.service;

import com.sparta.instagramProject.dto.CommentRequestDto;
import com.sparta.instagramProject.model.Comment;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.Member;
import com.sparta.instagramProject.model.Time;
import com.sparta.instagramProject.repository.MemberRepository;
import com.sparta.instagramProject.repository.ArticleRepository;
import com.sparta.instagramProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor //final로 선언한 변수가 있으면 꼭 생성해달라는 것
@Service
public class CommentService {


    private final CommentRepository commentRepository; // [2번]update메소드 작성 전에 id에 맞는 값을 찾으려면 find를 써야하는데 find를 쓰기위해서는 Repository가 있어야한다.
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final MemberRepository memberRepository;

    public String getTimeMsg() {
        Date now = new Date();
        return Time.calculateTime(now);
    }

    public String getNickname() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Secured("ROLE_USER")
    @Transactional
    public Comment addComment(Long articleId, CommentRequestDto commentRequestDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        String nickname = getNickname();
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .article(article)
                .nickname(nickname)
                .build();
        commentRepository.save(comment);
        article.setCommentCnt((long)article.getCommentList().size());
        comment.setTimeMsg(Time.calculateTime(comment.getCreatedAt()));
        return comment;

    }



    @Transactional
    public Boolean deleteComment(Long articleId, Long commentId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if(!articleService.getNickname().equals(comment.getNickname())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        article.deleteComment(comment);
        commentRepository.delete(comment);
        return true;
    }

}
//    @Transactional
//    public Comment updateComment(Long articleId, Long commentId, CommentRequestDto commentRequestDto) {
//        if(!articleRepository.findById(articleId).isPresent())
//            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
//
//        if(!getNickname().equals(comment.getNickname())) {
//            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
//        }
//        comment.setContent(commentRequestDto);
//        return comment;
//    }
