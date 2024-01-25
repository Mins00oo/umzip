package com.ssafy.umzip.global.util.jwt;

import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.util.redis.RedisService;
import com.ssafy.umzip.global.util.security.MemberDetailService;
import com.ssafy.umzip.global.util.security.MemberDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final MemberDetailService memberDetailService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_HEADER = "Refresh";

    private final RedisService redisService;
    private static String secretKey;

    private static Long accessExpiredTimeMs;

    private static Long refreshExpiredTimeMs;

    @Value("${jwt.secretKey}")
    public void setSecretKey(String secretKey) {
        JwtTokenProvider.secretKey = secretKey;
    }

    @Value("${jwt.accessExpiredTimeMs}")
    public void setAccessExpiredTimeMs(String accessExpiredTimeMs) {
        JwtTokenProvider.accessExpiredTimeMs = Long.valueOf(accessExpiredTimeMs);
    }

    @Value("${jwt.refreshExpiredTimeMs}")
    public void setRefreshExpiredTimeMs(String refreshExpiredTimeMs) {
        JwtTokenProvider.refreshExpiredTimeMs = Long.valueOf(refreshExpiredTimeMs);
    }

    public MemberTokenDto generateMemberToken(Member member) {
        MemberTokenDto tokenDto =
                MemberTokenDto.builder()
                        .accessToken(this.createAccessToken(member.getEmail(), Role.USER, member.getId(),
                                member.getSigungu(), secretKey, accessExpiredTimeMs))
                        .refreshToken(this.createRefreshToken(member.getEmail(), secretKey, refreshExpiredTimeMs))
                        .build();

        redisService.setValue(
                member.getEmail(), tokenDto.getRefreshToken(), Duration.ofMillis(refreshExpiredTimeMs));
        return tokenDto;
    }

    public MemberTokenDto generateCompanyToken(Company company) {
        MemberTokenDto tokenDto =
                MemberTokenDto.builder()
                        .accessToken(this.createAccessToken(company.getMember().getEmail(), company.getRole(),
                                company.getMember().getId(), company.getSigungu(), secretKey, accessExpiredTimeMs))
                        .refreshToken(this.createRefreshToken(company.getMember().getEmail(), secretKey, refreshExpiredTimeMs))
                        .build();

        redisService.setValue(
                company.getMember().getEmail(), tokenDto.getRefreshToken(), Duration.ofMillis(refreshExpiredTimeMs));
        return tokenDto;
    }

    public String createAccessToken(
            String email, Role role, Long id, int sigungu, String secretKey, Long accessExpiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("role", role);
        claims.put("id", id);
        claims.put("sigungu", sigungu);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredTimeMs))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(
            String email, String secretKey, Long refreshExpiredTimeMs) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiredTimeMs))
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

    public static void setRefreshTokenInHeader(String refreshToken, HttpServletResponse response) {
        response.setHeader(REFRESH_HEADER, refreshToken);
    }

    public String getUserEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public Long getMemberId(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public Authentication getAuthentication(String token) {
        MemberDetailsImpl memberDetails = (MemberDetailsImpl) memberDetailService.loadUserByUsername(this.getUserEmail(token));

        return new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
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

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // 여기서 구체적인 예외 종류에 따라 다르게 로깅할 수도 있습니다.
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

}
