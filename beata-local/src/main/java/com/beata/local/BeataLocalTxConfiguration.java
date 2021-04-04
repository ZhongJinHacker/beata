package com.beata.local;


import com.beata.local.aspect.LocalTransactionAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@ComponentScan(basePackages = {"com.beata.local"})
@Configuration
public class BeataLocalTxConfiguration {

    @Bean
    @DependsOn("transactionManager")
    public LocalTransactionAspect localTransactionAspect(DataSourceTransactionManager transactionManager) {
        return new LocalTransactionAspect(transactionManager);
    }
}
