package pe.tcloud.shikotsu.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.tcloud.shikotsu.config.JwtConfiguration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtil {
    private final JwtConfiguration jwtConfiguration;
    private final Algorithm algorithm;

    public JwtUtil(JwtConfiguration jwtConfiguration) {
        this.algorithm = Algorithm.HMAC256(jwtConfiguration.secret());
        this.jwtConfiguration = jwtConfiguration;
    }

    public String generateToken(String email, String company) {
        log.debug("Generating jwt token for {}", email);
        var now = LocalDateTime.now().plusYears(1);
        return JWT.create()
                .withSubject(jwtConfiguration.subject())
                .withClaim("email", email)
                .withClaim("company", company)
                .withIssuedAt(new Date())
                .withIssuer(jwtConfiguration.issuer())
                .withExpiresAt(now.toInstant(ZoneOffset.UTC))
                .sign(algorithm);
    }

    public List<String> validateTokenAndRetrieveSubject(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withSubject(jwtConfiguration.subject())
                .withIssuer(jwtConfiguration.issuer())
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return List.of(jwt.getClaim("email").asString(), jwt.getClaim("company").asString());
    }
}
