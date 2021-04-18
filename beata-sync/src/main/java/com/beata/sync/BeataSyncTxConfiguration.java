package com.beata.sync;

import com.beata.sync.aspect.GlobalSyncTransactionAspect;
import com.beata.sync.properties.BeataServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.beata.sync"})
@Configuration
public class BeataSyncTxConfiguration {

    @Bean
    public GlobalSyncTransactionAspect globalSyncTransactionAspect(BeataServerProperties serverProperties) {
        return new GlobalSyncTransactionAspect(serverProperties);
    }


}
