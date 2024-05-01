package ecnic.service.configs.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ecnic.service.models.entities.EcnicUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtService {

    @Value("api.security.token.secret")
    private String secret;

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
