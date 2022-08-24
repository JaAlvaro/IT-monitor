package com.monitor.app.service;

import com.monitor.app.model.Machine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Machine service.
 */
public interface MachineService {


    /**
     * Insert mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<String> insert(String machineId);

    /**
     * Check id mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Boolean> checkId(String machineId);

    /**
     * Find mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Machine> find(String machineId);

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);

    /**
     * Find machines by user flux.
     *
     * @param username the username
     * @return the flux
     */
    Flux<Machine> findMachinesByUser(String username);
}
