
package com.mrgindustria.website.config;

import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Bean
    public DataSource dataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(datasourceUrl);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
