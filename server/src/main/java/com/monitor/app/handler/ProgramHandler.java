package com.monitor.app.handler;

import com.monitor.app.model.ProgramList;
import com.monitor.app.service.impl.MachineServiceImpl;
import com.monitor.app.service.impl.ProgramServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * The type Program handler.
 */
@Component
public class ProgramHandler {

    @Autowired
    private ProgramServiceImpl programService;

    @Autowired
    private MachineServiceImpl machineService;

    /**
     * Handle program list post request mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> handleProgramPostRequest(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(ProgramList.class)
                .flatMap(programList -> machineService.checkId(programList.machineId()).flatMap(isValid -> isValid ? Mono.just(programList) : Mono.empty()))
                .flatMap(programService::insert)
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR - Machine ID given has not been registered yet.")));
    }

}
