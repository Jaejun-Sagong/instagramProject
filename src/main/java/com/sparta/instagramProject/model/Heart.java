package com.sparta.instagramProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "heart")
@Entity
@AllArgsConstructor
@Builder
public class Heart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.IDENTITY : ID값이 서로 영향없이 자기만의 테이블 기준으로 올라간다.
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @JoinColumn(name = "articleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonBackReference
    private Article article;

}
