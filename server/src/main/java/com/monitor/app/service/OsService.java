package com.monitor.app.service;

import com.monitor.app.model.Os;
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
    Mono<String> insert(Os os);

    /**
     * Find flux.
     *
     * @param machineId the machine id
     * @return the flux
     */
    Mono<Os> find(String machineId);

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);
}
