package com.monitor.app.service;

import com.monitor.app.model.Battery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Battery service.
 */
public interface BatteryService {

    /**
     * Save mono.
     *
     * @param battery the battery
     * @return the mono
     */
    Mono<String> insert(Battery battery);

    /**
     * Find flux.
     *
     * @param machineId the machine id
     * @return the flux
     */
    Flux<Battery> find(String machineId);

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);
}
