package com.medilabo_solutions.gateway.listener;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.medilabo_solutions.gateway.constant.UriToIgnore;

import reactor.core.publisher.Mono;

@Component
public class EndpointListener implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(EndpointListener.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestURL = java.net.URLDecoder.decode(exchange.getRequest().getURI().toString(), StandardCharsets.UTF_8);

        if (!UriToIgnore.URI_TO_IGNORE.contains(exchange.getRequest().getPath().value())) {
            if (!exchange.getRequest().getQueryParams().isEmpty()) {
                String requestParameters = "?" + exchange.getRequest().getQueryParams().entrySet().stream()
                        .map(e -> e.getKey() + "=" + String.join(", ", e.getValue()))
                        .collect(Collectors.joining(" "));
                logger.info("URL requested: {} {}{}", exchange.getRequest().getMethod(), requestURL, requestParameters);
            } else {
                logger.info("Endpoint requested: {} {}", exchange.getRequest().getMethod(), requestURL);
            }
        }

        return chain.filter(exchange).doOnTerminate(() -> {
            int responseStatus = exchange.getResponse().getStatusCode().value();

            if (!UriToIgnore.URI_TO_IGNORE.contains(exchange.getRequest().getPath().value())) {
                switch (responseStatus) {
                    case 200:
                        logger.info("Response: Status {} OK", responseStatus);
                        break;
                    case 201:
                        String responseLocation = java.net.URLDecoder.decode(exchange.getResponse().getHeaders().getFirst("Location"), StandardCharsets.UTF_8);
                        logger.info("Response: Status {} Created - Location : {}", responseStatus, responseLocation);
                        break;
                    case 204:
                        logger.info("Response: Status {} No Content.", responseStatus);
                        break;
                    case 400:
                        logger.error("Response: Status {} Bad Request.", responseStatus);
                        break;
                    case 404:
                        logger.error("Response: Status {} Not Found.", responseStatus);
                        break;
                    case 500:
                        logger.error("Response: Status {} Internal Server Error.", responseStatus);
                        break;
                    default:
                        logger.error("Status unknown");
                }
            }
        });
    }
}
