package com.monitor.app.client.service;

import com.monitor.app.client.model.Os;
import reactor.core.publisher.Mono;

/**
 * The interface Os service.
 */
public interface OsService {

    /**
     * Send os info.
     */
    Mono<String> monitorOsInfo();
}
