package com.monitor.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;


/**
 * The type Security configuration.
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    /**
     * Api http security security web filter chain.
     *
     * @param http the http
     * @return the security web filter chain
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        return http.csrf().disable()
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/*"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().authenticated()
                ).httpBasic().and().formLogin().disable().build();
    }

    /**
     * Web http security security web filter chain.
     *
     * @param http the http
     * @return the security web filter chain
     */
    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
        return http.csrf().disable()
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/register", "/styles/**", "/scripts/**", "/images/**","/login", "/*.ico").permitAll()
                        .anyExchange().authenticated())
                .formLogin().loginPage("/login").and()
                .build();
    }

}