package com.grady.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.grady.stock")
@SpringBootApplication
public class StockApp {

    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }
}
