package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.service.MachineService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public Mono<Boolean> checkId(String id) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT EXISTS (SELECT 1 FROM machine WHERE ID = '" + id + "')").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> row.get(0)))
                .next()
                .map(result -> String.valueOf(result).equals("1"));
    }

    @Override
    public Mono<Machine> find(String id) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM machine WHERE ID = '" + id + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> Machine.builder()
                        .machineId(id)
                        .register_date(row.get("register_date", String.class))
                        .timeStamp(row.get("last_timestamp", String.class))
                        .build()))
                .next();
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }


}

