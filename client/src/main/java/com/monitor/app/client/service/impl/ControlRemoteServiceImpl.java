package com.monitor.app.client.service.impl;

import com.fasterxml.classmate.AnnotationOverrides;
import com.monitor.app.client.model.Battery;
import com.monitor.app.client.model.RemoteControl;
import com.monitor.app.client.service.BatteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.monitor.app.client.util.Constant.BATTERY_URL;
import static com.monitor.app.client.util.Constant.CONTROL_URL;
import static com.monitor.app.client.util.Util.*;

/**
 * The type Cpu service.
 */
@Service
@Slf4j
public class ControlRemoteServiceImpl implements BatteryService {


    private final WebClient webClient;

    /**
     * Instantiates a new Cpu service.
     *
     * @param webClientBuilder the web client builder
     */
    public ControlRemoteServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
    }

    @Override
    public Mono<String> monitorRemoteControl() {
        return Mono.just(buildControlRemoteRequest())
                .flatMap(this::sendRemoteControlRequest);
        // TODO procesar comando en cmd
    }

    private RemoteControl buildControlRemoteRequest() {
        return RemoteControl.builder()
                .machineId(getMachineId())
                .timeStamp(getDatetime())
                .command("")
                .build();
    }

    private Mono<String> sendRemoteControlRequest(RemoteControl request) {
        log.info("URL: " + CONTROL_URL);
        log.info("Requesting remote control... " + request);
        return webClient.post()
                .uri(CONTROL_URL)
                .headers(h -> h.addAll(buildHttpHeaders("CONTROL")))
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, e ->
                        Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ERROR - Client error while requesting Remote Control. Please, make sure you have registered this machine with the ID " + getMachineId())))
                .onStatus(HttpStatus::is5xxServerError, e ->
                        Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR - Server error while requesting Remote Control")))
                .bodyToMono(String.class);
    }
}
