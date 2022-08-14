package com.monitor.app.service;

import com.monitor.app.model.Cpu;
import com.monitor.app.model.Os;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Os service.
 */
public interface OsService {

    /**
     * Save mono.
     *
     * @param os the os
     * @return the mono
     */
    Mono<String> save(Os os);

    /**
     * Find all flux.
     *
     * @return the flux
     */
    Flux<Os> findAll();

    /**
     * Delete all mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> deleteAll(String machineId);

}
