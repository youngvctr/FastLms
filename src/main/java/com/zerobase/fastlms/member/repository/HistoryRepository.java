package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<LoginHistory, Integer> {
    List<LoginHistory> findLoginHistoriesByUserIdOrderByLoginDateTimeDesc(String userId);
}
