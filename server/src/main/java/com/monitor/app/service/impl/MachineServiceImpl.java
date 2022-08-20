package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.service.MachineService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * The type Machine service.
 */
@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    @Override
    public Mono<String> insert(Machine machine) {
        return null;
    }

    @Override
    public Mono<Boolean> checkId(String id) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT ID FROM machine WHERE ID = '" + id + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> row.get("id", String.class)))
                .next()
                .map(row -> row.equals(id));
    }

    @Override
    public Mono<Machine> find(String machineId) {
        return null;
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }


}

