package com.monitor.app.service;

import com.monitor.app.model.Program;
import com.monitor.app.model.ProgramList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Program service.
 */
public interface ProgramService {

    /**
     * Save mono.
     *
     * @param programList the program list
     * @return the mono
     */
    Mono<String> insert(ProgramList programList);

    /**
     * Find flux.
     *
     * @param machineId the machine id
     * @return the flux
     */
    Flux<Program> find(String machineId);

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);
}
