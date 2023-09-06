package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Transactional
    public List<BannerDto> list() {
        List<Banner> banners = bannerRepository.findAll();

        return banners.stream()
                .map(BannerDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean add(BannerDto bannerDto) {
        if(bannerDto.getIsDisplayed()==null) bannerDto.setIsDisplayed("off");

        Banner banner = Banner.builder()
                .bannerName(bannerDto.getBannerName())
                .imagePath(bannerDto.getImagePath())
                .alterText(bannerDto.getAlterText())
                .linkUrl(bannerDto.getLinkUrl())
                .target(bannerDto.getTarget())
                .bannerOrder(bannerDto.getBannerOrder())
                .isDisplayed(bannerDto.getIsDisplayed())
                .regDateTime(LocalDateTime.now())
                .build();

        bannerRepository.save(banner);

        return true;
    }

    @Override
    public boolean del(String idList) {

        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }

        return true;
    }
}
