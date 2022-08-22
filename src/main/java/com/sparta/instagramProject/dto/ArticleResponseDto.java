package com.sparta.instagramProject.dto;

import com.sparta.instagramProject.model.Comment;
import com.sparta.instagramProject.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArticleResponseDto {
    private long id;
    private String nickname;
    private String content;
    private long commentCnt;
    private List<Comment> commentList;
    private long heartCnt;
    private String timeMsg;
    private List<Image> imgList;
    private boolean isLike;
    private String createdAt;

}
