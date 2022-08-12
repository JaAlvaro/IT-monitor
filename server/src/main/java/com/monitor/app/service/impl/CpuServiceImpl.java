package com.monitor.app.service.impl;

import com.monitor.app.model.Cpu;
import com.monitor.app.service.CpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CpuServiceImpl implements CpuService {

    //TODO JDBC
    //private JDBC dbConnection;

    @Override
    public Mono<String> save(Cpu cpu) {
        return null;
    }

    @Override
    public Flux<Cpu> findAll() {
        return null;
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }
}
