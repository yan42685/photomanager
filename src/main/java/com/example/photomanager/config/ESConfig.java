package com.example.photomanager.config;

import lombok.Setter;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author LuckyCurve
 * @date 2020/5/2 14:22
 */
@Setter
@Configuration
@ConfigurationProperties(prefix = "es")
public class ESConfig extends AbstractElasticsearchConfiguration {

    private String host;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(
                ClientConfiguration.builder().connectedTo(host).build())
                .rest();
    }
}
