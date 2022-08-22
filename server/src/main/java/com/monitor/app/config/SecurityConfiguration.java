package com.monitor.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        return http.csrf().disable()
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/**"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().authenticated()
                ).httpBasic().and().formLogin().disable().build();
    }

    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
        return http.authorizeExchange((authorize) -> authorize
                        .pathMatchers("/register/user", "/login", "/*.png", "/*.css", "/*.js", "/*.ico").permitAll()
                        .anyExchange().authenticated())
                .formLogin().loginPage("/login").and()
                .build();
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails monitor = User.builder()
                .username("monitor")
                .password("{noop}monitor")
                .roles("MONITOR")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("ADMIN", "MONITOR")
                .build();

        return new MapReactiveUserDetailsService(monitor, admin);
    }

    /*
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }*/
}