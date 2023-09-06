package com.zerobase.fastlms.member.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LoginHistory {

    @Id
    @GeneratedValue
    private Integer id;
    private String userId;

    @CreatedDate
    private LocalDateTime loginDateTime;

    private String ip;
    private String userAgent;
}