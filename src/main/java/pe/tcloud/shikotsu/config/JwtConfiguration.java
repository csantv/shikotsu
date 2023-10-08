package pe.tcloud.shikotsu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {
    private String secret;
    private String issuer;
    private String subject;
}
