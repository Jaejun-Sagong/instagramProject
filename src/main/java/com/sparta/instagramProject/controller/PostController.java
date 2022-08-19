package com.sparta.instagramProject.controller;

import com.sparta.instagramProject.dto.CampRequestDto;
import com.sparta.instagramProject.jwt.TokenProvider;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.service.CampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", exposedHeaders = "*", allowedHeaders = "*")
@RestController // MemoController도 어딘가에서 쓰일 때 new MemoController 이렇게 해서 생성이 되고 사용되어야 하는데 이 어노테이션으로 그 작업을 생략하게 해줌
public class PostController {  //생성 조회 변경 삭제가 필요한데 업데이트 -> service , 나머지 ->Repo가 필요함

    private final CampService campService;
    private final TokenProvider tokenProvider;


    /////////////////////
//, consumes = {MediaType.ALL_VALUE}
    @Secured("ROLE_USER")
    @DeleteMapping(value = "/delete")
    public void removeS3Image() {
        campService.removeS3Image();
    }

/////////////////////

    @Secured("ROLE_USER")
    @PostMapping(value = "/api/auth/camp", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Article registerCamp(@Valid @RequestPart(value = "dto") CampRequestDto requestDto,
                                @RequestPart(required = false) MultipartFile multipartFile) throws IOException {

        return campService.registerCamp(requestDto, multipartFile);
    }
//    @Secured("ROLE_USER")
//    @PostMapping("/api/auth/camp")
//    public Camp registerCamp(@RequestBody CampRequestDto requestDto) throws IOException {
//
//        return campService.registerCamp(requestDto);
//    }

    @GetMapping("/api/camp")
    public ResponseEntity getCamps(HttpServletResponse response, HttpServletRequest request) {
        if (request.getHeader("Authorization") != null) {
            response.setHeader("nickname", campService.getNickname());
            return ResponseEntity.ok(campService.getCamps());
        } else {
            response.setHeader("Authorization", null);
            return ResponseEntity.ok(campService.getCamps());
        }
    }

    @GetMapping("/api/camp/{campid}")
    public Article showCampDetail(@PathVariable Long campid) {
        return campService.showCampDetail(campid);
    }

    @Secured("ROLE_USER")
    @PutMapping("/api/auth/camp/{campid}")
    public Article updateMemo(@PathVariable Long campid, @RequestBody CampRequestDto requestDto) {   //RequestBody어노테이션을 써줘야만 Request 안에 Body를 requestDto에 넣어줘야하구나 를 Spring이 안다
        return campService.update(campid, requestDto);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/api/auth/camp/{campid}")
    public boolean deleteMemo(@PathVariable Long campid) {   //RequestBody어노테이션을 써줘야만 Request 안에 Body를 requestDto에 넣어줘야하구나 를 Spring이 안다
        return campService.delete(campid);
    }
}
