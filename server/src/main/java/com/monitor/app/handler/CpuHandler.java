package com.monitor.app.handler;

import com.monitor.app.model.Cpu;
import com.monitor.app.service.impl.CpuServiceImpl;
import com.monitor.app.service.impl.MachineServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                .flatMap(cpu -> machineService.checkId(cpu.machineId()).flatMap(isValid -> isValid? Mono.just(cpu) : Mono.empty()))
                .flatMap(cpuService::save)
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR - Machine ID given has not been registered yet.")));
    }


}
