package com.ssafy.umzip.global.config;

import com.ssafy.umzip.global.exception.CustomAccessDeniedHandler;
import com.ssafy.umzip.global.exception.CustomAuthenticationEntryPoint;
import com.ssafy.umzip.global.util.jwt.JwtTokenFilter;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import com.ssafy.umzip.global.util.security.CustomAuthenticationFilter;
import com.ssafy.umzip.global.util.security.MemberDetailService;
import com.ssafy.umzip.global.util.security.handler.AuthFailureHandler;
import com.ssafy.umzip.global.util.security.handler.AuthSuccessHandler;
import com.ssafy.umzip.global.util.security.handler.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final AuthSuccessHandler successHandler;
    private final AuthFailureHandler failureHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailService memberDetailService;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);

        http
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("**")
                                        .permitAll()// /api/v1/login 경로를 모든 사용자에게 허용
                                        .anyRequest().authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(
                        logout -> logout
                                        .logoutUrl("/api/logout")
                                        .logoutSuccessHandler(logoutSuccessHandler)
                );
//                .addFilterBefore(jwtTokenFilter, LogoutFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(memberDetailService);
        filter.setFilterProcessesUrl("/api/login");
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
