package com.codeit.side.common.adapter.out.security;

import com.codeit.side.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(Map.of("type", "ACCESS"), userDetails, jwtUtil.getAccessExpiration());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(Map.of("type", "REFRESH"), userDetails, jwtUtil.getRefreshExpiration());
    }

    public boolean isRefreshToken(String token) {
        return extractClaim(token, claims -> claims.get("type")).equals("REFRESH");
    }

    public String generateToken(UserDetails userDetails, long expirationTime) {
        return generateToken(new HashMap<>(), userDetails, expirationTime);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expirationTime
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenInvalid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return !(username.equals(userDetails.getUsername()));
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtUtil.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtUtil.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}