package com.monitor.app.client.service;

import reactor.core.publisher.Mono;

/**
 * The interface Battery service.
 */
public interface BatteryService {

    /**
     * Send battery info.
     */
    Mono<String> monitorRemoteControl();
}
