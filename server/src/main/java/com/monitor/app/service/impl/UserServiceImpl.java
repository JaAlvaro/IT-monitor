package com.monitor.app.service.impl;

import com.monitor.app.model.User;
import com.monitor.app.service.UserService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public Mono<String> insert(User user) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createBatch()
                        .add(format("INSERT INTO user VALUES ('%s', '%s', '%s')", user.name(), user.password(), user.register_date()))
                        .execute())
                .next()
                .map(result -> {
                    log.info("SUCCESS - Saved User: " + Util.decrypt(user.name(), user.register_date()));
                    return "SUCCESS - User received and saved";
                });
    }

    @Override
    public Mono<User> find(String machineId) {
        return null;
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }

    //public Mono<Boolean> checkPassword
}
