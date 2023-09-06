package com.zerobase.fastlms.admin.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Banner {

    @Id
    @GeneratedValue
    private Long id;
    private String bannerName;
    private String imagePath;
    private String alterText;
    private String linkUrl;
    private String target;
    private String bannerOrder;
    private String isDisplayed;

    @CreatedDate
    private LocalDateTime regDateTime;

}
