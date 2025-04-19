package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secretKey;

    private long expirationMs;

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
    }
}
