package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.dto.UserLoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;

import java.util.List;

public interface HistoryService {
    void saveLogOnLogin(LoginHistory loginHistory);
    List<UserLoginHistoryDto> getUserLoginHistoryDto(String userId);
}
