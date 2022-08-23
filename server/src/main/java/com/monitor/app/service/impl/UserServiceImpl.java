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
                        .add(format("INSERT INTO user VALUES ('%s', '%s', '%s')",
                                user.name(),
                                Util.encrypt(user.password()), //TODO quitar
                                //passwordEncoder.encode(user.password()),
                                user.register_date()))
                        .execute())
                .next()
                .map(result -> {
                    log.info("SUCCESS - Saved User: " + user.name());
                    return "SUCCESS - User received and saved";
                });
    }

    @Override
    public Mono<User> find(String username) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM user WHERE NAME = '" + username + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> User.builder()
                        .name((String) row.get(0))
                        .password((String) row.get(1))
                        .register_date((String) row.get(2))
                        .build()))
                .next();
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }

    @Override
    public Mono<Boolean> checkUser(String name) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT EXISTS (SELECT 1 FROM user WHERE NAME = '" + name + "')").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> row.get(0)))
                .next()
                .map(result -> String.valueOf(result).equals("1"));
    }

}
