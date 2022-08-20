package com.monitor.app.client.service;

import reactor.core.publisher.Mono;


/**
 * The interface Program service.
 */
public interface ProgramService {


    /**
     * Monitor program list mono.
     *
     * @return the mono
     */
    Mono<String> monitorProgramList();
}
