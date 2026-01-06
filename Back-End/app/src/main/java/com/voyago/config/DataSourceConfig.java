package com.voyago.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    
    @Value("${POSTGRES_URL}")
    private String url;
    
    @Value("${POSTGRES_SELECT_USERNAME}")
    private String selectUser;
    
    @Value("${POSTGRES_SELECT_PASSWORD}")
    private String selectPass;
    
    @Value("${POSTGRES_INSERT_USERNAME}")
    private String insertUser;
    
    @Value("${POSTGRES_INSERT_PASSWORD}")
    private String insertPass;
    
    @Value("${POSTGRES_UPDATE_USERNAME}")
    private String updateUser;
    
    @Value("${POSTGRES_UPDATE_PASSWORD}")
    private String updatePass;
    
    @Value("${POSTGRES_DELETE_USERNAME}")
    private String deleteUser;
    
    @Value("${POSTGRES_DELETE_PASSWORD}")
    private String deletePass;
    
    @Primary
    @Bean
    public DataSource dataSource() {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DatabaseOperation.SELECT, createDataSource(selectUser, selectPass));
        dataSourceMap.put(DatabaseOperation.INSERT, createDataSource(insertUser, insertPass));
        dataSourceMap.put(DatabaseOperation.UPDATE, createDataSource(updateUser, updatePass));
        dataSourceMap.put(DatabaseOperation.DELETE, createDataSource(deleteUser, deletePass));
        
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(createDataSource(selectUser, selectPass));
        
        return routingDataSource;
    }
    
    private DataSource createDataSource(String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setMaximumPoolSize(5);
        return dataSource;
    }
}
