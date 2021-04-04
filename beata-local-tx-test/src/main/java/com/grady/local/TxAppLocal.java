package com.grady.local;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@MapperScan("com.grady.local.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TxAppLocal {

    public static void main(String[] args) {
        SpringApplication.run(TxAppLocal.class, args);
    }
}
