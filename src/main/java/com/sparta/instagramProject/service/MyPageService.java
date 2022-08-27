package com.sparta.instagramProject.service;


import antlr.ASTNULLType;
import com.sparta.instagramProject.dto.ArticleResponseDto;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.Heart;
import com.sparta.instagramProject.model.Time;
import com.sparta.instagramProject.repository.ArticleRepository;
import com.sparta.instagramProject.repository.CommentRepository;
import com.sparta.instagramProject.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final HeartRepository heartRepository;
    private final ArticleRepository articleRepository;

    public String CreatedAtCustom(Timestamp timestamp){
        String timestampToString = timestamp.toString();
//        String timestampToString = "2022-08-21T14:54:46.247+00:00";
        String customTimestamp = timestampToString.substring(5,10);
        customTimestamp = customTimestamp.replace("-","월 ");
        if(customTimestamp.startsWith("0")){
            customTimestamp = customTimestamp.substring(1);
        }
        System.out.println(customTimestamp);
        return customTimestamp;
    }

    public List<ArticleResponseDto> getMyArticlePage(UserDetails userDetails) {
        String nickname = userDetails.getUsername();
        List<Article> articleList = articleRepository.findAllByNickname(nickname);
        List<ArticleResponseDto> responseDtos = new ArrayList<>();
        for (Article article : articleList) {
            article.setTimeMsg(Time.calculateTime(article.getCreatedAt()));  //주석처리해도 될 것 같음 or TimeMsg를 필드로 안가져도 될 것 같음.(Response를 모두 Dto로 하면서 Dto에서 Time.calculate를 사용할 경우)
            if (heartRepository.existsByNicknameAndArticle(nickname, article)) {
                article.setIsLike(true);
            }
            responseDtos.add(ArticleResponseDto.builder()
                    .id(article.getId())
                    .commentCnt(article.getCommentList().size())
                    .imgList(article.getImgList())
                    .heartCnt(article.getHeartList().size())
                    .content(article.getContent())
                    .timeMsg(article.getTimeMsg())
                    .isLike(article.getIsLike())
                    .nickname(article.getNickname())
                    .createdAt(CreatedAtCustom(article.getCreatedAt()))
                    .build());
        }
        return responseDtos.stream().sorted(Comparator.comparing(ArticleResponseDto::getId).reversed()).collect(Collectors.toList());
    }
    public List<ArticleResponseDto> getMyHeartPage(UserDetails userDetails) {
        String nickname = userDetails.getUsername();
        //유저가 좋아요 누른 Article이 담긴 heartList 반환
        List<Heart> heartList = heartRepository.findAllByNickname(nickname);
        List<ArticleResponseDto> responseDtos = new ArrayList<>();
        for (Heart heart : heartList) {
            Article article = heart.getArticle();
            article.setTimeMsg(Time.calculateTime(article.getCreatedAt()));
            responseDtos.add(ArticleResponseDto.builder()
                    .id(article.getId())
                    .commentCnt((long) article.getCommentList().size())
                    .imgList(article.getImgList())
                    .heartCnt((long) article.getHeartList().size())
                    .content(article.getContent())
                    .timeMsg(article.getTimeMsg())
                    .isLike(true)
                    .nickname(article.getNickname())
                    .createdAt(CreatedAtCustom(article.getCreatedAt()))
                    .build());
        }
        return responseDtos.stream().sorted(Comparator.comparing(ArticleResponseDto::getId).reversed()).collect(Collectors.toList());
    }
}


