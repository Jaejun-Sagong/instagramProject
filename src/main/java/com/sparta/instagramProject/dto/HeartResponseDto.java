package com.sparta.instagramProject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeartResponseDto {
    private long articleId;
    private long heartCnt;
    private boolean isLike;
}
