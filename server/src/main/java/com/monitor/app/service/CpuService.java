package com.monitor.app.service;

import com.monitor.app.model.Cpu;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Cpu service.
 */
public interface CpuService {

    /**
     * Save mono.
     *
     * @param cpu the cpu
     * @return the mono
     */
    Mono<String> save(Cpu cpu);

    /**
     * Find all flux.
     *
     * @return the flux
     */
    Flux<Cpu> findAll();

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);

}
