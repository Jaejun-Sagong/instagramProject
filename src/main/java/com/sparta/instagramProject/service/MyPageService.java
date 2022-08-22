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
import java.util.List;

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
                    .commentCnt((long) article.getCommentList().size())
                    .imgList(article.getImgList())
                    .heartCnt((long) article.getHeartList().size())
                    .content(article.getContent())
                    .timeMsg(article.getTimeMsg())
                    .isLike(article.getIsLike())
                    .nickname(article.getNickname())
                    .createdAt(CreatedAtCustom(article.getCreatedAt()))
                    .build());
        }
        return responseDtos;
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
        return responseDtos;
    }
}
//    @Transactional
//    public MyPageDto readPage() {
//        int zero =0;
//        int one = 1;
//        String nickname = memoService.getNickname();
//        List<Memo> memoList = memoRepository.findAllByMemberName(nickname);
//        List<Comment> commentList = commentRepository.findAllByMemberNameAndParent_IdIsNull(nickname);
//        List<Comment> childcommentList = commentRepository.findAllByMemberNameAndParent_IdIsNotNull(nickname);
//
//        List<Heart> heartcheckmemoList = heartRepository.findAllByNicknameAndMemo_IdIsNotNull(nickname);
//
//        List<Memo> heartmemoList = new ArrayList<>();
//        for(int i=0; i<heartcheckmemoList.size(); i++){
//            heartmemoList.add(heartcheckmemoList.get(i).getMemo()); // 게시글
//        }
//
//        List<Heart> heartcheckcommentList = heartRepository.findAllByNicknameAndMemo_IdIsNull(nickname);
//        List<Comment> heartcommentList = new ArrayList<>();
//        List<Comment> heartchildcommentList = new ArrayList<>();
//        for(int i=0; i<heartcheckcommentList.size(); i++){
//            if(heartcheckcommentList.get(i).getComment().getParent() == null)
//                heartcommentList.add(heartcheckcommentList.get(i).getComment()); // 댓글
//            else
//                heartchildcommentList.add(heartcheckcommentList.get(i).getComment()); // 대댓글
//        }
//
//        MyPageDto myPageDto = new MyPageDto();
//
//        for(Memo memo : memoList){
//            // 값을넣고 // 값을넣는과정에서 걔가
//            MemoTfDto memoTfDto = new MemoTfDto(memo);
//            myPageDto.addMomoTfDto(memoTfDto);
//            // 찾은값에 set해주기
//        }
//        for(Comment comment : commentList){
//            CommentTfDto commentTfDto = new CommentTfDto(comment);
//            myPageDto.addCommentTfDto(commentTfDto);
//        }
//        for(Comment childComment : childcommentList){
//            CommentTfDto commentTfDto = new CommentTfDto(childComment);
//            myPageDto.addChildCommentTfDto(commentTfDto);
//        }
//        for(Memo heartmemo : heartmemoList){
//            MemoTfDto memoTfDto = new MemoTfDto(heartmemo);
//            myPageDto.addHeartmemoTfDto(memoTfDto);
//        }
//        for(Comment heartcomment : heartcommentList){
//            CommentTfDto commentTfDto = new CommentTfDto(heartcomment);
//            myPageDto.addHeartcommentTfDto(commentTfDto);
//        }
//        for(Comment heartchildComment : heartchildcommentList){
//            CommentTfDto commentTfDto = new CommentTfDto(heartchildComment);
//            myPageDto.addHeartchildCommentTfDto(commentTfDto);
//        }

//        List<Memo> heartmemoList = new ArrayList<>();
//        for(int i=0; i<heartcheckmemoList.size(); i++){
//            heartmemoList.add(heartcheckmemoList.get(i).getMemo());
//        }
//        List<Heart> heartcheckcommentList = heartRepository.findAllByNicknameAndParent(nickname,zero);
//        List<Comment> heartcommentList = new ArrayList<>();
//        for(int i=0; i<heartcheckcommentList.size(); i++){
//            heartcommentList.add(heartcheckcommentList.get(i).getComment());
//        }
//        List<Heart> heartcheckchildcommentList = heartRepository.findAllByNicknameAndParent(nickname,one);
//        List<Comment> heartchildcommentList = new ArrayList<>();
//        for(int i=0; i<heartcheckchildcommentList.size(); i++){
//            heartchildcommentList.add(heartcheckchildcommentList.get(i).getComment());
//        }
//        List<MyPageDto> myPageDtos = new ArrayList<>();
//        for(int i=0; i<memoList.size(); i++){
//            myPageDtos.add(new MyPageDto(memoList.get(i)));
//            myPageDtos.get(i).setMemo(memoList.get(i));
//        }
//        for(int i=0; i<commentList.size(); i++){
//            myPageDtos.get(i).setComment(commentList.get(i));
//        }
//        for(int i=0; i<childcommentList.size(); i++){
//            myPageDtos.get(i).setChildComment(childcommentList.get(i));
//        }
//        for(int i=0; i<heartmemoList.size(); i++){
//            myPageDtos.get(i).setHeartmemo(heartmemoList.get(i));
//        }
//        for(int i=0; i<heartcommentList.size(); i++){
//            myPageDtos.get(i).setHeartcomment(heartcommentList.get(i));
//        }
//        for(int i=0; i<heartchildcommentList.size(); i++){
//            myPageDtos.get(i).setHeartchildComment(heartchildcommentList.get(i));
//        }
//        return myPageDto;
//    }


