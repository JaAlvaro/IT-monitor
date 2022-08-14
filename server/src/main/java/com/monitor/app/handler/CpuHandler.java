package com.monitor.app.handler;

import com.monitor.app.model.Cpu;
import com.monitor.app.service.impl.CpuServiceImpl;
import com.monitor.app.service.impl.MachineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * The type Cpu handler.
 */
@Component
public class CpuHandler {

    @Autowired
    private CpuServiceImpl cpuService;

    @Autowired
    private MachineServiceImpl machineService;

    /**
     * Handle cpu post request mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> handleCpuPostRequest(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Cpu.class)
                .filter(this::validateRequest)
                .flatMap(cpuService::save)
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    /**
     * Method to check if machineID from CpuInfo request exists
     *
     * @param cpuInfo the cpuInfo
     * @return the boolean
     */
    private boolean validateRequest(Cpu cpuInfo) {
        return machineService.checkId(cpuInfo.machineId());
    }
}
