package com.grady.sync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.grady.sync.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SyncTxApp {

    public static void main(String[] args) {
        SpringApplication.run(SyncTxApp.class, args);
    }

}