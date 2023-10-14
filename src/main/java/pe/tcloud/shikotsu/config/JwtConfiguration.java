package pe.tcloud.shikotsu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfiguration (
        String secret,
        String issuer,
        String subject
) {}
