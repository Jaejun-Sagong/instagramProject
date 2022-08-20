package com.sparta.instagramProject.controller;

import com.sparta.instagramProject.dto.ArticleRequestDto;
import com.sparta.instagramProject.dto.ArticleResponseDto;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", exposedHeaders = "*", allowedHeaders = "*")
@RestController // MemoController도 어딘가에서 쓰일 때 new MemoController 이렇게 해서 생성이 되고 사용되어야 하는데 이 어노테이션으로 그 작업을 생략하게 해줌
public class ArticleController {  //생성 조회 변경 삭제가 필요한데 업데이트 -> service , 나머지 ->Repo가 필요함

    private final ArticleService articleService;
//    private final TokenProvider tokenProvider;


    /////////////////////
//, consumes = {MediaType.ALL_VALUE}
    @Secured("ROLE_USER")
    @DeleteMapping(value = "/delete")
    public void removeS3Image() {
        articleService.removeS3Image();
    }

/////////////////////

    @Secured("ROLE_USER")
    @PostMapping(value = "/api/auth/article", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Boolean registerArticle(@Valid @RequestPart(value = "dto") ArticleRequestDto requestDto,
                                @RequestPart(required = false) List<MultipartFile> multipartFile) throws IOException {

        return articleService.registerArticle(requestDto, multipartFile);
    }

    @Secured("ROLE_USER")
    @GetMapping("/api/auth/article")
    public List<ArticleResponseDto> getArticles(HttpServletResponse response, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        System.out.println(username);
        response.setHeader("nickname", username);
        return articleService.getArticles(username);
    }

    @GetMapping("/api/article/{articleId}")
    public Article showArticleDetail(@PathVariable Long articleId) {
        return articleService.showArticleDetail(articleId);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/api/auth/article/{articleId}")
    public boolean deleteArticle(@PathVariable Long articleId) {   //RequestBody어노테이션을 써줘야만 Request 안에 Body를 requestDto에 넣어줘야하구나 를 Spring이 안다
        return articleService.delete(articleId);
    }
}
//    @Secured("ROLE_USER")
//    @PutMapping("/api/auth/article/{articleId}")
//    public Article updateMemo(@PathVariable Long articleId, @RequestBody ArticleRequestDto requestDto) {   //RequestBody어노테이션을 써줘야만 Request 안에 Body를 requestDto에 넣어줘야하구나 를 Spring이 안다
//        return articleService.update(articleId, requestDto);
//    }