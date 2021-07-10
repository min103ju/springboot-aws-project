package com.citizen.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// JPA Auditing 활성화
// EnableJpaAuditing를 사용하기 위해선 Entity가 최소 하나가 필요하다.
@EnableJpaAuditing
public class JpaConfig {
}
