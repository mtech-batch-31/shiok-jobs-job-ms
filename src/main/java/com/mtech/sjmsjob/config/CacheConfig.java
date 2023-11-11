package com.mtech.sjmsjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    public static final String JOBS = "JOBS";

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(JOBS);
        return cacheManager;
    }

    @CacheEvict(allEntries = true, value = {JOBS})
    @Scheduled(fixedDelay = 5 * 60 * 1000 ,  initialDelay = 500)
    public void reportCacheEvict() {
        log.debug("Flush Cache " + DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
    }

}