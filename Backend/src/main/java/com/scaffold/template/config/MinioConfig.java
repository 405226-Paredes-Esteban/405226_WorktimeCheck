package com.scaffold.template.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    private String endpoint = "http://localhost:9000";

    private String accessKey = "minioadmin";

    private String secretKey = "minioadmin";

    /**
     * Creates and configures a MiniOClient bean.
     *
     * @return A MiniOClient instance connected to the specified endpoint.
     */
    @Bean
    public MinioClient miniOClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
