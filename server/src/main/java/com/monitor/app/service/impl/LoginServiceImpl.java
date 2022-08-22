package com.monitor.app.service.impl;

import com.monitor.app.service.LoginService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoginServiceImpl implements LoginService {


    @Override
    public Mono<String> login() {
        return null;
    }
}
