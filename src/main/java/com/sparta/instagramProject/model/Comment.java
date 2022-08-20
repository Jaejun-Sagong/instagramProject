package com.sparta.instagramProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.instagramProject.Timestamped;
import com.sparta.instagramProject.dto.CommentRequestDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private String timeMsg;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "articleId", nullable = false)
    private Article article;
}
