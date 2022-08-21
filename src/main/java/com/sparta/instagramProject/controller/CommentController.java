package com.sparta.instagramProject.controller;

import com.sparta.instagramProject.dto.CommentRequestDto;
import com.sparta.instagramProject.model.Comment;
import com.sparta.instagramProject.repository.CommentRepository;
import com.sparta.instagramProject.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", exposedHeaders = "*", allowedHeaders = "*")
@RequestMapping("/api/auth/comment")
public class CommentController {

    private final CommentService commentService;  // 필수적인 요소이기 때문에 final 선언
    private final CommentRepository commentRepository;


    @Secured("ROLE_USER")
    @PostMapping("/{articleId}")
    public Comment addComment(@PathVariable Long articleId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.addComment(articleId, commentRequestDto);
    }

//    @Secured("ROLE_USER")
//    @PutMapping("/{id}/{commentId}")
//    public Comment updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
//        return commentService.updateComment(id, commentId, commentRequestDto);
//    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{id}/{commentId}")
    public Boolean deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        return commentService.deleteComment(id, commentId);
    }




}


//    @GetMapping("/api/memos")
//    public List<Memo> readMemo(){
//        return memoRepository.findAllByOrderByModifiedAtDesc();
//    }
//}
