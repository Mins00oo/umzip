package com.ssafy.umzip.global.util.jwt;

import com.ssafy.umzip.global.common.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static String secretKey;

    private static Long accessExpiredTimeMs;

    @Value("${jwt.secretKey}")
    public void setSecretKey(String secretKey) {
        JwtTokenProvider.secretKey = secretKey;
    }

    @Value("${jwt.accessExpiredTimeMs}")
    public void setAccessExpiredTimeMs(String accessExpiredTimeMs) {
        JwtTokenProvider.accessExpiredTimeMs = Long.valueOf(accessExpiredTimeMs);
    }

    public String createAccessToken(
            String email, Role role, Long memberId) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("role", role);
        claims.put("id", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredTimeMs))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Key getKey(String secretKey) {
        byte[] keyByte = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public static void setAccessTokenInHeader(String accessToken, HttpServletResponse response) {
        response.setHeader(AUTHORIZATION_HEADER, "Bearer " + accessToken);
    }

    public static String getUserEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public Long getMemberId(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

}
