package com.sparta.instagramProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleRequestDto {
    private String nickname;
    private String content;
}
