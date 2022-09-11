package com.monitor.app.service.impl;

import com.monitor.app.model.RemoteControl;
import com.monitor.app.service.RemoteControlService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class RemoteControlServiceImpl implements RemoteControlService {

    @Override
    public Mono<String> insert(RemoteControl control) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement(format("INSERT INTO REMOTE_CONTROL VALUES ('%s', '%s', '%s')",
                                control.machineId(), control.timeStamp(), control.command()))
                        .execute()).next()
                .map(insertion -> {
                    log.info("SUCCESS - Saved Remote Control: " + control);
                    return "SUCCESS - Remote Control received and saved";
                });
    }

    @Override
    public Flux<RemoteControl> find(String machineId) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM REMOTE_CONTROL WHERE MACHINE_ID = '" + machineId + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> RemoteControl.builder()
                        .machineId(machineId)
                        .timeStamp(row.get("timestamp", String.class))
                        .command(row.get("command", String.class))
                        .build()));
    }

    @Override
    public Mono<RemoteControl> findMono(String machineId) {
        return find(machineId).next();
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }
}
