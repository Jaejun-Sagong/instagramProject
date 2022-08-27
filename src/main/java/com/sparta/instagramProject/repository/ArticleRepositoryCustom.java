package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.dto.ArticleResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {
    Slice<ArticleResponseDto> getArticleScroll(Pageable pageable, String nickname);
}
