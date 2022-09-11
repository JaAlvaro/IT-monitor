package com.monitor.app.web.handler;

import com.monitor.app.model.RemoteControl;
import com.monitor.app.service.impl.MachineServiceImpl;
import com.monitor.app.service.impl.RemoteControlServiceImpl;
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
 * The type Control handler.
 */
@Component
@Slf4j
public class ControlHandler {

    @Autowired
    private RemoteControlServiceImpl controlService;

    @Autowired
    private MachineServiceImpl machineService;


    /**
     * Handle control request mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> handleRemoteControlRequest(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RemoteControl.class)
                .flatMap(control -> machineService.checkId(control.machineId()).flatMap(isValid -> isValid ? Mono.just(control.machineId()) : Mono.empty()))
                //.flatMap(controlService::findMono)
                .map(id -> {
                    log.info("SUCCESS - Null control available - " + id);
                    return "SUCCESS - Null Remote control available";
                })
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR - Machine ID given has not been registered yet.")));
    }

}
