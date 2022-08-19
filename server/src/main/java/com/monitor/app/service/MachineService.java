package com.monitor.app.service;

import reactor.core.publisher.Mono;

/**
 * The interface Machine service.
 */
public interface MachineService {

    /**
     * Check id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    Mono<Boolean> checkId(String id);
}
