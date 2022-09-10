package com.monitor.app.web.handler;

import com.monitor.app.model.Battery;
import com.monitor.app.service.impl.BatteryServiceImpl;
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
 * The type Battery handler.
 */
@Component
public class BatteryHandler {

    @Autowired
    private BatteryServiceImpl batteryService;

    @Autowired
    private MachineServiceImpl machineService;


    /**
     * Handle battery request mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> handleBatteryRequest(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Battery.class)
                .flatMap(battery -> machineService.checkId(battery.machineId()).flatMap(isValid -> isValid ? Mono.just(battery) : Mono.empty()))
                .flatMap(batteryService::insert)
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR - Machine ID given has not been registered yet.")));
    }

}
