package com.sparta.instagramProject.service;

import com.amazonaws.services.s3.AmazonS3;
import com.sparta.instagramProject.dto.CampRequestDto;
import com.sparta.instagramProject.dto.S3Dto;
import com.sparta.instagramProject.image.S3Uploader;
import com.sparta.instagramProject.model.Article;
import com.sparta.instagramProject.model.DeletedUrlPath;
import com.sparta.instagramProject.model.Member;
import com.sparta.instagramProject.repository.CampRepository;
import com.sparta.instagramProject.repository.DeletedUrlPathRepository;
import com.sparta.instagramProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor //final로 선언한 변수가 있으면 꼭 생성해달라는 것
@Service
public class CampService {

    private final CampRepository campRepository;
    private final S3Uploader s3Uploader;
    private final AmazonS3 amazonS3Client;
    private final DeletedUrlPathRepository deletedUrlPathRepository;
    private final MemberRepository memberRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름


    public String getNickname() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> member = memberRepository.findById(Long.valueOf(userId));
        return member.get().getUserId();
    }

    @Transactional
    public Article registerCamp(CampRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        String nickname = getNickname();
        if (multipartFile != null) {
            S3Dto s3Dto = s3Uploader.upload(multipartFile);
            Article article = Article.builder()
                    .nickname(nickname)
                    .title(requestDto.getTitle())
                    .location(requestDto.getLocation())
                    .review(requestDto.getReview())
                    .urlPath(s3Dto.getFileName())
                    .imgUrl(s3Dto.getUploadImageUrl())
                    .build();

            campRepository.save(article);

            return article;
        }
        Article article = Article.builder()
                    .nickname(nickname)
                .title(requestDto.getTitle())
                .location(requestDto.getLocation())
                .review(requestDto.getReview())
                .build();
        return campRepository.save(article);
    }
//@Transactional
//public Camp registerCamp(CampRequestDto requestDto) throws IOException {
//    String nickname = getNickname();
//        Camp camp = Camp.builder()
//                .nickname(nickname)
//                .title(requestDto.getTitle())
//                .location(requestDto.getLocation())
//                .review(requestDto.getReview())
//                .build();
//
//        campRepository.save(camp);
//
//        return camp;
//    }
    //S3 필요없는 곳
    public List<Article> getCamps() {

        return campRepository.findAll();
//        List<Camp> campList = campRepository.findAll();
//        List<CampDto> campDtos = new ArrayList<>();
//        for (Camp camp : campList) {
////            CampDto campDto = CampMapper.INSTANCE.campToDto(camp);
//            campDtos.add(camp);
//        }
//        return campDtos;
    }

    public Article showCampDetail(Long campid) {
        return campRepository.findById(campid)
                .orElseThrow(() -> new IllegalArgumentException("해당 추천글이 존재하지 않습니다."));
    }


    @Transactional
    public Article update(Long id, CampRequestDto requestDto) {
        Article article = campRepository.findById(id).orElseThrow( //[3번]  수정할 id에 해당하는 데이터를 repo에서 찾고 해당id를 갖는 memo를 호출한다.
                () -> new IllegalArgumentException("추천글이 존재하지 않습니다")
        );
        if (!getNickname().equals(article.getNickname())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        article.update(requestDto);
        return article;
    }

    public boolean delete(Long campid) {
        Article article = campRepository.findById(campid).orElseThrow(
                () -> new IllegalArgumentException("메모가 존재하지 않습니다")
        );
        if (!getNickname().equals(article.getNickname())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
            DeletedUrlPath deletedUrlPath = new DeletedUrlPath();
            deletedUrlPath.setDeletedUrlPath(article.getUrlPath());
            deletedUrlPathRepository.save(deletedUrlPath);
//            removeS3Image();

        campRepository.deleteById(campid);
        return true;
    }

    public void removeS3Image() {
        List<DeletedUrlPath> deletedUrlPathList = deletedUrlPathRepository.findAll();
        for (DeletedUrlPath deletedUrlPath : deletedUrlPathList) {
            s3Uploader.remove(deletedUrlPath.getDeletedUrlPath());
        }
        deletedUrlPathRepository.deleteAll();
    }
}