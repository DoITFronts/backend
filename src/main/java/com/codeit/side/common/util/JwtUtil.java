package com.codeit.side.common.util;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Getter
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private SecretKey secretKey;

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.access_expiration}")
    private long accessExpiration;

    @Value("${spring.jwt.refresh_expiration}")
    private long refreshExpiration;

    @PostConstruct
    public void init() {
        secretKey = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String username, String nickname, Long expiration) {
        return Jwts.builder()
                .claim("sub", username)
                .claim("nickname", nickname)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }
}
