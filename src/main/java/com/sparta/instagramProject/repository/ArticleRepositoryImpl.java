package com.sparta.instagramProject.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.instagramProject.dto.ArticleResponseDto;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.QArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.instagramProject.model.QArticle.*;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final HeartRepository heartRepository;

    public ArticleRepositoryImpl(EntityManager em, HeartRepository heartRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.heartRepository = heartRepository;
    }


    public String CreatedAtCustom(Timestamp timestamp) {
        String timestampToString = timestamp.toString();
//        String timestampToString = "2022-08-21T14:54:46.247+00:00";
        String customTimestamp = timestampToString.substring(5, 10);
        customTimestamp = customTimestamp.replace("-", "월 ");
        if (customTimestamp.startsWith("0")) {
            customTimestamp = customTimestamp.substring(1);
        }
        System.out.println(customTimestamp);
        return customTimestamp;
    }

    public Slice<ArticleResponseDto> getArticleScroll(Pageable pageable, String nickname) {
        QueryResults<Article> result = queryFactory
                .selectFrom(article)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(article.id.desc())
                .fetchResults();

        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        for (Article eachArticle : result.getResults()) {
            articleResponseDtoList.add(ArticleResponseDto.builder()
                    .id(eachArticle.getId())
                    .commentCnt(eachArticle.getCommentList().size())
                    .imgList(eachArticle.getImgList())
                    .heartCnt(eachArticle.getHeartList().size())
                    .content(eachArticle.getContent())
                    .timeMsg(eachArticle.getTimeMsg())
                    .isLike(heartRepository.findByNicknameAndArticle(nickname,eachArticle).isPresent())
                    .nickname(eachArticle.getNickname())
                    .createdAt(CreatedAtCustom(eachArticle.getCreatedAt()))
                    .build());
        }

        boolean hasNext = false;
        if (articleResponseDtoList.size() > pageable.getPageSize()) {
            articleResponseDtoList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(articleResponseDtoList, pageable, hasNext);
    }
}
