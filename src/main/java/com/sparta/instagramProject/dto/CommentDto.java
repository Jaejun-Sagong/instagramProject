package com.sparta.instagramProject.dto;

import com.sparta.instagramProject.model.Comment;
import lombok.Getter;

@Getter
public class CommentDto {

    private Long id;

    private String content;

    private String memberName;


}