package com.monitor.app.handler;

import com.monitor.app.model.Os;
import com.monitor.app.service.impl.MachineServiceImpl;
import com.monitor.app.service.impl.OsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * The type Os handler.
 */
@Component
public class OsHandler {

    @Autowired
    private OsServiceImpl osService;

    @Autowired
    private MachineServiceImpl machineService;

    /**
     * Handle os post request mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> handleOsPostRequest(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Os.class)
                .flatMap(os -> machineService.checkId(os.machineId()).flatMap(isValid -> isValid ? Mono.just(os) : Mono.empty()))
                .flatMap(osService::insert)
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR - Machine ID given has not been registered yet.")));
    }

}
