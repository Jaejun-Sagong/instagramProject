package com.sparta.instagramProject.controller;

import com.sparta.instagramProject.dto.ArticleResponseDto;
import com.sparta.instagramProject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", exposedHeaders = "*", allowedHeaders = "*")
@RequestMapping("/api/auth/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @Secured("ROLE_USER")
    @GetMapping("myarticle")
    public List<ArticleResponseDto> getMyArticlePage(@AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.getMyArticlePage(userDetails);
    }
    @Secured("ROLE_USER")
    @GetMapping("myheart")
    public List<ArticleResponseDto> getMyHeartPage(@AuthenticationPrincipal UserDetails userDetails) {
        return myPageService.getMyHeartPage(userDetails);
    }


}

