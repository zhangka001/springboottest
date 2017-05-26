package com.zrb.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by zrb on 2017/5/20.
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }
}
