package com.sparta.instagramProject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "atricle")
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Article { // 생성,수정 시간을 자동으로 만들어줍니다.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Image> imgUrlList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)  //부모가 삭제될 때 자식들도 다 삭제되는 어노테이션
    @JsonManagedReference //DB연관관계 무한회귀 방지
    private List<Comment> commentList;


    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
    public void deleteComment(Comment comment) {
        commentList.remove(comment);
    }

//    public void update(CampRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.review = requestDto.getReview();
//        this.location = requestDto.getLocation();
//    }
}
