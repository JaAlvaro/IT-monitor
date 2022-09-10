package com.monitor.app.service.impl;

import com.monitor.app.model.Cpu;
import com.monitor.app.service.CpuService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class CpuServiceImpl implements CpuService {

    @Autowired
    private MachineServiceImpl machineService;

    @Override
    @PreAuthorize("hasRole('MONITOR')")
    public Mono<String> insert(Cpu cpu) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement(format("INSERT INTO CPU VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                cpu.machineId(), cpu.timeStamp(), cpu.model(), cpu.microarchitecture(), cpu.logicalCores(),
                                cpu.physicalCores(), cpu.temperature(), cpu.load()))
                        .execute()).next()
                .flatMap(insertion -> {
                    log.info("SUCCESS - Saved CPU: " + cpu);
                    return machineService.updateLastConnection(cpu.machineId(), cpu.timeStamp());
                })
                .switchIfEmpty(Mono.just("SUCCESS - CPU received and saved"));
    }

    @Override
    public Flux<Cpu> find(String machineId) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM cpu WHERE MACHINE_ID = '" + machineId + "' ORDER BY TIMESTAMP DESC LIMIT 20").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> Cpu.builder()
                        .machineId(machineId)
                        .timeStamp(row.get("timestamp", String.class))
                        .model(row.get("model", String.class))
                        .microarchitecture(row.get("microarchitecture", String.class))
                        .logicalCores(row.get("logical_cores", String.class))
                        .physicalCores(row.get("physical_cores", String.class))
                        .temperature(row.get("temperature", String.class))
                        .load(row.get("usage_load", String.class))
                        .build()));
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }
}
