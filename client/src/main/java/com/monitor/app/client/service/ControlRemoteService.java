package com.monitor.app.client.service;

import reactor.core.publisher.Mono;

/**
 * The interface Control remote service.
 */
public interface ControlRemoteService {

    /**
     * Get control remote.
     */
    Mono<String> monitorControlRemote();
}
