package com.monitor.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.find(username)
                .map(user -> {
                    var aux = user.name();
                    return User.builder()
                            .username(user.name())
                            .password("{bcrypt}" + user.password())
                            //.password("{noop}")
                            .roles(user.name().equals("monitor") ? "MONITOR": "USER")
                            //.roles("MONITOR")
                            .build();
                });
    }

}
