package com.monitor.app.service.impl;

import com.monitor.app.model.Cpu;
import com.monitor.app.service.CpuService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class CpuServiceImpl implements CpuService {

    @Override
    public Mono<String> insert(Cpu cpu) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement(format("INSERT INTO CPU VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                cpu.machineId(), cpu.timeStamp(), cpu.name(), cpu.microarchitecture(), cpu.logicalCores(),
                                cpu.physicalCores(), cpu.temperature(), cpu.load()))
                .execute())
                .next()
                .map(result -> {
                    log.info("SUCCESS - Saved CPU: " + cpu);
                    return "SUCCESS - CPU received and saved";
                });
    }

    @Override
    public Flux<Cpu> findAll(String machineId) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(String machineId) {
        return null;
    }
}
