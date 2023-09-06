package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.member.dto.UserLoginHistoryDto;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.repository.HistoryRepository;
import com.zerobase.fastlms.member.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    public void saveLogOnLogin(LoginHistory loginHistory) {
        historyRepository.save(loginHistory);
    }

    public List<UserLoginHistoryDto> getUserLoginHistoryDto(String userId) {

        List<LoginHistory> loginHistories = historyRepository.findLoginHistoriesByUserIdOrderByLoginDateTimeDesc(userId);

        return loginHistories.stream().map(UserLoginHistoryDto::fromEntity).collect(Collectors.toList());
    }
}
