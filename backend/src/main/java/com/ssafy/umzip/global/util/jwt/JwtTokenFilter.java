package com.ssafy.umzip.global.util.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.global.common.BaseResponse;
import com.ssafy.umzip.global.common.StatusCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> PERMIT_URLS =
            List.of("/api/users/**", "/api/login", "/api/auth/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        String path = request.getServletPath();
        return PERMIT_URLS.stream()
                .anyMatch(exclude -> pathMatcher.match(exclude, path));
    }

    @Override
    public void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws IOException, ServletException {

        try {
            String AUTHORIZATION_HEADER = "Authorization";
            final String header = request.getHeader(AUTHORIZATION_HEADER);

            if (header == null || !header.startsWith("Bearer ")) {
                log.error("Authorization Header does not start with Bearer {}", request.getRequestURI());
            }

            final String token = header.split(" ")[1].trim();
            if (JwtTokenProvider.isExpired(token)) {
                log.error("Authorization Bearer Token Expired {}", request.getRequestURI());
            }
            Authentication authenticationToken = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            log.info("Token verification successful. URI: {}", request.getRequestURI());
        } catch (SignatureException e) {
            setErrorResponse(response, StatusCode.INVALID_TOKEN);
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            setErrorResponse(response, StatusCode.DAMAGED_ACCESS_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            setErrorResponse(response, StatusCode.EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            setErrorResponse(response, StatusCode.UNSUPPORTED_ACCESS_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            setErrorResponse(response, StatusCode.ILLEGAL_ARGUMENT_TOKEN);
        } catch (NullPointerException e) {
            log.error("JWT  is empty: {}", e.getMessage());
            setErrorResponse(response, StatusCode.INVALID_NULL_TOKEN);
        }

        chain.doFilter(request, response);
    }

    public static void setErrorResponse(HttpServletResponse response, StatusCode statusCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        ObjectMapper objectMapper = new ObjectMapper();

        BaseResponse<Object> baseResponse = new BaseResponse<>(statusCode);

        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}

