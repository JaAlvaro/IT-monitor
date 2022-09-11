package com.monitor.app.service;

import com.monitor.app.model.RemoteControl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Remote control service.
 */
public interface RemoteControlService {

    /**
     * Save mono.
     *
     * @param control the control
     * @return the mono
     */
    Mono<String> insert(RemoteControl control);


    /**
     * Find all flux.
     *
     * @param machineId the machine id
     * @return the flux
     */
    Flux<RemoteControl> find(String machineId);

    /**
     * Find mono mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<RemoteControl> findMono(String machineId);

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);

}
