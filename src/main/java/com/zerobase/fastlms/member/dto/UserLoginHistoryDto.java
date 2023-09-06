package com.zerobase.fastlms.member.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginHistoryDto {

    private Integer id;
    private String userId;
    private LocalDateTime loginDateTime;
    private String ip;
    private String userAgent;

    public static UserLoginHistoryDto fromEntity(LoginHistory loginHistory) {
        return UserLoginHistoryDto.builder()
                .id(loginHistory.getId())
                .userId(loginHistory.getUserId())
                .ip(loginHistory.getIp())
                .userAgent(loginHistory.getUserAgent())
                .loginDateTime(loginHistory.getLoginDateTime())
                .build();
    }
}
