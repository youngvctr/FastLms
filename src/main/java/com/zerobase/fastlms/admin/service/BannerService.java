package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;

import java.util.List;

public interface BannerService {
    List<BannerDto> list();
    
    /**
     * 신규 배너 추가
     */
    
    boolean add(BannerDto bannerDto);

    /**
     * 배너 삭제
     */
    boolean del(String idList);
}
