//package com.sparta.instagramProject.service;
//
//import com.sparta.hanghae99clone.dto.request.PostRequestDto;
//import com.sparta.hanghae99clone.dto.response.PostListResponseDto;
//import com.sparta.hanghae99clone.model.Image;
//import com.sparta.hanghae99clone.model.Post;
//import com.sparta.hanghae99clone.model.User;
//import com.sparta.hanghae99clone.repository.CommentRepository;
//import com.sparta.hanghae99clone.repository.FavoriteRepository;
//import com.sparta.hanghae99clone.repository.ImageRepository;
//import com.sparta.hanghae99clone.repository.PostRepository;
//import com.sparta.hanghae99clone.utill.Calculator;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class PostService {
//
//    private final ImageRepository imageRepository;
//    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
//    private final Calculator calculator;
//    private final FavoriteRepository favoriteRepository;
//
//    public PostListResponseDto save(String content,
//                                    String uploadImageUrl,
//                                    User user) {
//
//        // 게시글 저장
//        Post post = new Post(user, content);
//        postRepository.save(post);
//
//        // 이미지 저장
//        Image image = new Image(uploadImageUrl, post);
//        imageRepository.save(image);
//        //Dto 담기
//        long dayBefore = ChronoUnit.MINUTES.between(post.getCreatedAt(), LocalDateTime.now());
//        PostListResponseDto postListResponseDto=new PostListResponseDto(post,image,calculator.time(dayBefore),0L, 0L, false, post.getUser().getImageSrc());
//        return postListResponseDto;
//    }
//
//    public List<PostListResponseDto> findAll(User user,Integer pageid) {
//        Pageable pageable= PageRequest.of(pageid,5, Sort.by((Sort.Direction.DESC),"id"));
//        Page<Post> allPost = postRepository.findAll(pageable);
//
//        List<PostListResponseDto> postResponseDtos = new ArrayList<>();
//
//        for (Post post : allPost) {
//            // 게시글에 해당하는 이미지 불러오기
//            Image image = imageRepository.findByPost(post).orElseThrow(
//                    () -> new IllegalArgumentException("이미지가 존재하지 않습니다.")
//            );
//            // 게시글에 존재하는 댓글의 수
//            Long commentCnt =  (long)commentRepository.findByPost(post).size();
//
//            Long favoriteCnt = (long)favoriteRepository.findByPost(post).size();
//
//            // 몇 분 전에 게시글이 작성되었는지 확인
//            long dayBefore = ChronoUnit.MINUTES.between(post.getCreatedAt(), LocalDateTime.now());
//
//            // 게시글의 존재 유무
//            boolean favoriteStatus = favoriteRepository.existsByPostAndUserId(post, user.getId());
//            postResponseDtos.add(new PostListResponseDto(post, image, calculator.time(dayBefore), commentCnt, favoriteCnt, favoriteStatus, post.getUser().getImageSrc()));
//
//        }
//        return postResponseDtos;
//    }
//
//
//
//    // 특정 게시글 수정
//    @Transactional
//    public void edit(Long postId,
//                     PostRequestDto postRequestDto) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
//        );
//
//        post.editContent(postRequestDto);
//    }
//
//    public void remove(Long postId) {
//        System.out.println("postId = " + postId);
//        List<Post> all = postRepository.findAll();
//        for (Post post : all) {
//            System.out.println("post.getId() = " + post.getId());
//            System.out.println("post.getId().equals(postId) = " + post.getId().equals(postId));
//        }
//        postRepository.deleteById(postId);
//    }
//}