package ecnic.service.configs.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ecnic.service.models.entities.EcnicUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Validated
@ConfigurationProperties(prefix = "api.security.token")
public class JwtService {

    @NotNull
    private final String secret;

    JwtService(String secret) {
        this.secret = secret;
    }

    private final String ISSUER = "auth-api";

    public String generateToken(EcnicUser ecnicUser) {
        return JWT.create()
                .withIssuer(this.ISSUER)
                .withSubject(ecnicUser.getUsername())
                .withExpiresAt(this.calculateExpiration())
                .sign(this.getAlgorithm());
    }

    public String validateToken(String token) {
        return JWT.require(this.getAlgorithm())
                .withIssuer(this.ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant calculateExpiration() {
        int EXPIRATION_HOURS = 2;
        String ZONE_OFFSET = "+07:00";
        return LocalDateTime.now()
                .plusHours(EXPIRATION_HOURS)
                .toInstant(ZoneOffset.of(ZONE_OFFSET));
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secret);
    }
}
