package com.sparta.instagramProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.instagramProject.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.IDENTITY : ID값이 서로 영향없이 자기만의 테이블 기준으로 올라간다.
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "articleId", nullable = false)
    private Article article;


    public void setComment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
