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
    private Long id;
    private String nickname;
    private String content;
    private Long commentCnt;
    private Long heartCnt;
    private String timeMsg;
    private List<Image> imgList;
    private boolean isLike;
    private Timestamp createdAt;
}
