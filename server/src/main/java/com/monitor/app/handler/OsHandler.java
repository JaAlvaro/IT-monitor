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
 * The type Cpu handler.
 */
@Component
public class OsHandler {

    @Autowired
    private OsServiceImpl osService;

    @Autowired
    private MachineServiceImpl machineService;

    /**
     * Handle cpu post request mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> handleOsPostRequest(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Os.class)
                .filter(this::validateRequest)
                .flatMap(osService::save)
                .flatMap(str -> ok().bodyValue(str))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR - Machine ID given has not been registered yet.")));
    }

    /**
     * Method to check if machineID from OsInfo request exists
     *
     * @param osInfo the osInfo
     * @return the boolean
     */
    private boolean validateRequest(Os osInfo) {
        return machineService.checkId(osInfo.machineId());
    }
}
