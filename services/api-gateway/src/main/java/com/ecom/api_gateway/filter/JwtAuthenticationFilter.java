package com.ecom.api_gateway.filter;

import com.ecom.api_gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("ANNMOLLLL");
        String path = exchange.getRequest().getURI().getPath();
        System.out.println(">>>> Jwt Filter Executed, PATH = " + path);

        // PUBLIC ENDPOINTS
        if (path.startsWith("/auth/")
                || path.startsWith("/product/")
                || path.startsWith("/inventory/")
                || path.startsWith("/orders/")) {

            return chain.filter(exchange);
        }

        // CHECK AUTH HEADER
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        System.out.println("Auth Header = " + authHeader);
        System.out.println("PATH = " + path);

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);

            exchange = exchange.mutate()
                    .request(builder -> builder
                            .header("X-USER-ID", claims.get("id").toString())
                            .header("X-USER-ROLE", claims.get("role").toString())
                    ).build();

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
