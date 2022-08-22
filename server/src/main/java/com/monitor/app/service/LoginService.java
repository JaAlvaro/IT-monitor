package com.monitor.app.service;

import reactor.core.publisher.Mono;

public interface LoginService {
    Mono<String> login();
}
