package com.monitor.app.service.impl;

import com.monitor.app.model.Cpu;
import com.monitor.app.model.Os;
import com.monitor.app.service.CpuService;
import com.monitor.app.service.OsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OsServiceImpl implements OsService {


    @Override
    public Mono<String> save(Os os) {
        //TODO connection database
        log.info("SUCCESS - Saved OS: " + os);
        return Mono.just("SUCCESS - OS received and saved");
    }

    @Override
    public Flux<Os> findAll() {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(String machineId) {
        return null;
    }

}
