package com.monitor.app.service;

import com.monitor.app.model.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<String> insert(User user);

    Mono<User> find(String machineId);

    Mono<Void> delete(String machineId);
}
