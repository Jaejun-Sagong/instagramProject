//package com.sparta.instagramProject.repository;
//
//import com.querydsl.core.types.Order;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.PathBuilder;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.sparta.instagramProject.model.Article;
//import com.sparta.instagramProject.model.QArticle;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.SliceImpl;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.ObjectUtils;
//
//import java.util.ArrayList;
//
//import static com.sparta.instagramProject.model.QArticle.*;
//
//@Repository
//@RequiredArgsConstructor
//public class ProductRepositoryImpl implements ArticleRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public Slice<AritlceThumbResponse> findAllProductPageableOrderByCreatedAtDesc(Pageable pageable) {
//        JPAQuery<Article> productQuery= queryFactory
//                .selectFrom(article)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize()+1); //limit보다 한 개 더 들고온다.
//        for(Sort.Order o: pageable.getSort()) {
//            PathBuilder pathBuilder = new PathBuilder(article.getType(),article.getMetadata());
//            productQuery.orderBy(new OrderSpecifier(o.isAscending()? Order.ASC: Order.DESC,pathBuilder.get(o.getProperty())));
//        }
//        List<AritlceThumbResponse> content = new ArrayList<>(AritlceThumbResponse.toProductListResponse(productQuery.fetch()));
//        boolean hasNext =false;
//        //마지막 페이지는 사이즈가 항상 작다.
//        if(content.size() > pageable.getPageSize()) {
//            content.remove(pageable.getPageSize());
//            hasNext=true;
//        }
//        return new SliceImpl<>(content,pageable,hasNext);
//    }
//}