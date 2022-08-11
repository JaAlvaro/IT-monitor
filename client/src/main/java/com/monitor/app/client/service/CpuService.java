package com.monitor.app.client.service;

import reactor.core.publisher.Mono;

/**
 * The interface Cpu service.
 */
public interface CpuService {

    /**
     * Send cpu info.
     */
    Mono<String> monitorCpuInfo();
}
