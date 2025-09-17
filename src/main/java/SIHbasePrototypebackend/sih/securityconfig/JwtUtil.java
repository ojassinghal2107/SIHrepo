package SIHbasePrototypebackend.sih.securityconfig;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_very_long_secret_key_which_should_be_at_least_256_bits"; // Use env var in prod
    private final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
            .subject(username)
            .claim("roles", roles)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(key) // ✅ MacAlgorithm, not SignatureAlgorithm
            .compact();
    }

    public String extractUsername(String token) {
        return ((Claims) getClaims(token).getPayload()).getSubject(); // ✅ getPayload().getSubject()
    }

    public List<String> extractRoles(String token) {
        Object roles = getClaims(token).getPayload().get("roles");
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        }
        return List.of();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username);
    }


    private Jws<Claims> getClaims(String token) {
        return Jwts.parser()
            .verifyWith((key) ) // ✅ Cast to SecretKey
            .build()
            .parseSignedClaims(token); // ✅ New method in 0.12.x
    }
}