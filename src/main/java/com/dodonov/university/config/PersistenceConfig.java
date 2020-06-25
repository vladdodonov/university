package com.dodonov.university.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"com.dodonov.university.domain"})
@EnableJpaRepositories(basePackages = {"com.dodonov.university.repository"})
@EnableTransactionManagement
public class PersistenceConfig {
}
