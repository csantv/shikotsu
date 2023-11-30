package pe.tcloud.shikotsu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "aws")
public record AwsConfiguration (
        @NestedConfigurationProperty
        S3Configuration s3
) {
}
