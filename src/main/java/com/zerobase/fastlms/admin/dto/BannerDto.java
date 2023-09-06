package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerDto {

    private Long id;
    private String bannerName;
    private String imagePath;
    private String alterText;
    private String linkUrl;
    private String target;
    private String bannerOrder;
    private String isDisplayed;
    private LocalDateTime regDateTime;

    public static BannerDto from(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .imagePath(banner.getImagePath())
                .alterText(banner.getAlterText())
                .linkUrl(banner.getLinkUrl())
                .target(banner.getTarget())
                .bannerOrder(banner.getBannerOrder())
                .isDisplayed(banner.getIsDisplayed())
                .regDateTime(banner.getRegDateTime())
                .build();
    }
}
