package com.beata.sync.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "beata.server")
public class BeataServerProperties {

    private String host = "127.0.0.1";

    private String port = "8888";
}
