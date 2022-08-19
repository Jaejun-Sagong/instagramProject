package com.sparta.instagramProject.service;

import com.sparta.instagramProject.dto.CommentRequestDto;
import com.sparta.instagramProject.model.Comment;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.repository.CampRepository;
import com.sparta.instagramProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@RequiredArgsConstructor //final로 선언한 변수가 있으면 꼭 생성해달라는 것
@Service
public class CommentService {


    private final CommentRepository commentRepository; // [2번]update메소드 작성 전에 id에 맞는 값을 찾으려면 find를 써야하는데 find를 쓰기위해서는 Repository가 있어야한다.
    private final CampRepository campRepository;
    private final CampService campService;

    @Secured("ROLE_USER")
    @Transactional
    public Comment addComment(Long id, CommentRequestDto commentRequestDto) {
        Article article = campRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        String memberName = campService.getNickname();
        Comment comment = new Comment(article, memberName, commentRequestDto);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id, Long commentId, CommentRequestDto commentRequestDto) {
        Article article = campRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!campService.getNickname().equals(comment.getMemberName())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        comment.setComment(commentRequestDto);
        return comment;
    }

    @Transactional
    public Boolean deleteComment(Long id, Long commentId) {
        Article article = campRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if(!campService.getNickname().equals(comment.getMemberName())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        List<Comment> commentList = article.getCommentList();

        article.deleteComment(comment);
        commentRepository.delete(comment);
        return true;
    }






}

