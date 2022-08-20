package com.sparta.instagramProject.dto;

import com.sparta.instagramProject.model.Image;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArticleResponseDto {
    private long id;
    private String nickname;
    private String content;
    private long commentCnt;
    private long heartCnt;
    private String timeMsg;
    private List<Image> imgList;
    private boolean isLike;
    private Timestamp createdAt;
}
