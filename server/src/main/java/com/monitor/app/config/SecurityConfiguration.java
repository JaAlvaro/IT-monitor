package com.monitor.app.config;

import com.monitor.app.util.Util;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        return http.csrf().disable()
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/*"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().authenticated()
                ).httpBasic().and().formLogin().disable().build();
    }

    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
        return http.csrf().disable()
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/register/user*", "/login*", "/*.png", "/*.css", "/*.js", "/*.ico").permitAll()
                        .anyExchange().authenticated())
                .formLogin().loginPage("/login").and()
                .build();
    }

    //@Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$pCpEtZPqsfWMhwskheLDi.W2TEMBDs9CZLcJkgLgv7G414fdKbhEG")
                .roles("USER")
                .build();

        UserDetails monitor = User.builder()
                .username("monitor")
                .password("$2a$10$Scyrn1fUo5xW7NvjpdsV3OnQgC1S2LCo/ftwSSQQMv2mmp4XU6cCi")
                .roles("MONITOR")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$QOvSmDnMw4disZK0eaMWZ.dVBHL/UwK7WzQbxk3BX6NAxdHanHDHq")
                .roles("ADMIN", "MONITOR", "USER")
                .build();

        List<UserDetails> userList = new ArrayList<>(List.of(user, monitor, admin));

        Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM user").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> {
                    var usr = User.builder()
                            .username((String) row.get(0))
                            .password((String) row.get(1))
                            .roles("USER")
                            .build();
                    userList.add(usr);
                    return usr;
                }))
                //.doOnNext(userList::add)
                .subscribe();

        return new MapReactiveUserDetailsService(userList);
    }

    //@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}