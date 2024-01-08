package pe.tcloud.shikotsu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sunat")
public record SunatConfiguration (
        String baseUrl,
        String token
) {
}
