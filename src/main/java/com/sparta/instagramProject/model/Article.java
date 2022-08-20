package com.sparta.instagramProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @Column
    private Boolean isLike;

    //    @Column(nullable = false)
//    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    @Column
    private String timeMsg;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //부모가 삭제될 때 자식들도 다 삭제되는 어노테이션
    @JsonManagedReference //DB연관관계 무한회귀 방지
    private List<Image> imgList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> commentList;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
//    @JsonManagedReference
    private List<Heart> heartList;


    public void addImg(Image image) {
        this.imgList.add(image);
    }

    public void deleteImg(Image image) {
        this.imgList.remove(image);
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public void deleteComment(Comment comment) {
        commentList.remove(comment);
    }

    public void deleteHeart(Heart heart) {
        heartList.remove(heart);
    }

//    public void update(ArticleRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.review = requestDto.getReview();
//        this.location = requestDto.getLocation();
//    }
}
