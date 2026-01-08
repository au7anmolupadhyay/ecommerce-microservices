package com.ecom.payment_service.config;

import com.ecom.payment_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        log.info("➡️ Incoming request: {} {}", request.getMethod(), request.getRequestURI());
        log.info("➡️ Authorization header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("❌ No Bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.extractClaims(token);

            String userId = claims.getSubject();

            Object roleClaim = claims.get("roles");

            List<String> roles;

            if (roleClaim instanceof List<?> list) {
                roles = list.stream()
                        .map(String::valueOf)
                        .toList();
            } else if (claims.get("role") != null) {
                roles = List.of(String.valueOf(claims.get("role")));
            } else {
                roles = List.of();
            }

            log.info("✅ JWT subject (userId): {}", userId);
            log.info("✅ JWT roles from token: {}", roles);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> (GrantedAuthority)
                            new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();

            log.info("✅ Authorities mapped: {}", authorities);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("✅ Authentication set in SecurityContext");

        } catch (Exception ex) {
            log.error("❌ JWT validation failed", ex);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
