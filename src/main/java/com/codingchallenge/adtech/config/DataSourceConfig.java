package com.codingchallenge.adtech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String h2DataBaseUrl;

    @Value("${spring.datasource.driverClassName}")
    private String h2DriverClassName;

    @Value("${spring.datasource.username}")
    private String h2UserName;

    @Value("${spring.datasource.password}")
    private String h2Password;

    @Value("${spring.jpa.database-platform}")
    private String h2Dialect;

    @Bean
    public DataSource getH2DataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(h2DriverClassName);
        dataSourceBuilder.url(h2DataBaseUrl);
        dataSourceBuilder.username(h2UserName);
        dataSourceBuilder.password(h2Password);
        return dataSourceBuilder.build();
    }


}
