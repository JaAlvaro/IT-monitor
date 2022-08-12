package com.monitor.app.handler;

import com.monitor.app.model.Cpu;
import com.monitor.app.service.impl.CpuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CpuHandler{

    @Autowired
    private CpuServiceImpl service;

    public <T extends ServerResponse> Mono<T> handleCpuPostRequest(ServerRequest serverRequest) {
        serverRequest.bodyToMono(Cpu.class)
                .filter(this::validateRequest);

        //TODO ...
        return ok().bodyValue(service.save(serverRequest.bodyToMono(String.class)));
    }

    private boolean validateRequest(Cpu cpuInfo) {
        
        return true;
    }
}
