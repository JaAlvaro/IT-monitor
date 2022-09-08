package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.service.MachineService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.monitor.app.util.Util.getDatetime;
import static java.lang.String.format;

/**
 * The type Machine service.
 */
@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    @Override
    @PreAuthorize("hasRole('USER')")
    public Mono<String> insert(String id) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createBatch()
                        .add(format("INSERT INTO machine (ID, REGISTER_DATE) VALUES ('%s', '%s')", id, getDatetime()))
                        .execute())
                .next()
                .map(result -> {
                    log.info("SUCCESS - Saved Machine: " + id);
                    return "SUCCESS - Machine received and saved";
                });
    }

    @Override
    @PreAuthorize("hasRole('USER') || hasRole('MONITOR')")
    public Mono<Boolean> checkId(String id) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT EXISTS (SELECT 1 FROM machine WHERE ID = '" + id + "')").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> row.get(0)))
                .next()
                .map(result -> String.valueOf(result).equals("1"));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Mono<Machine> find(String id) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM machine WHERE ID = '" + id + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> Machine.builder()
                        .id(id)
                        .registerDate(row.get("register_date", String.class))
                        .lastConnection(row.get("last_connection", String.class))
                        .build()))
                .next();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Mono<Void> delete(String machineId) {
        return null;
    }

    @Override
    //@PreAuthorize("hasRole('USER')")
    public Flux<Machine> findMachinesByUser(String username) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT MACHINE_ID FROM user_machine WHERE USERNAME = '" + username+"'").execute())
                .flatMap(mySqlResult -> mySqlResult.map(((row, rowMetadata) -> (String) row.get(0))))
                .flatMap(this::find);
    }
}

