package pe.tcloud.shikotsu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "s3")
public record S3Configuration (
        String accessKey,
        String secretKey,
        String bucketName,
        String endpointUrl
){ }
