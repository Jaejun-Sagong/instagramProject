package com.sparta.instagramProject.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeartResponseDto {
    private long heartCnt;
    private boolean isLike;
}
