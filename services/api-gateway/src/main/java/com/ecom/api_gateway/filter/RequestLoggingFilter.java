package com.ecom.api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestLoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();
        log.info("Incoming Request: {} {}", method, path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() ->
                        log.info("Response Status: {}", exchange.getResponse().getStatusCode())
                ));
    }
}
