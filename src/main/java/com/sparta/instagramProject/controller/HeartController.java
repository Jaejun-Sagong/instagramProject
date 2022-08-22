package com.sparta.instagramProject.controller;

import com.sparta.instagramProject.dto.HeartResponseDto;
import com.sparta.instagramProject.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/heart")
@CrossOrigin(origins = "*", exposedHeaders = "*", allowedHeaders = "*")
public class HeartController {

    private final HeartService heartService;  // 필수적인 요소이기 때문에 final 선언

    @Secured("ROLE_USER")
    @PostMapping("/{articleId}")
    public HeartResponseDto addHeartToArticle(@PathVariable Long articleId) {
        return heartService.addHeartToArticle(articleId);
    }

}