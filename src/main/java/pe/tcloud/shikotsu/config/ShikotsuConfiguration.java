package pe.tcloud.shikotsu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shikotsu")
public record ShikotsuConfiguration(
        String baseUrl
) {
}
