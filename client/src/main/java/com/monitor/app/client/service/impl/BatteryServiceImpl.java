package com.monitor.app.client.service.impl;

import com.monitor.app.client.model.Battery;
import com.monitor.app.client.service.BatteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.PowerSource;
import reactor.core.publisher.Mono;

import static com.monitor.app.client.util.Constant.BATTERY_URL;
import static com.monitor.app.client.util.Util.*;

/**
 * The type Cpu service.
 */
@Service
@Slf4j
public class BatteryServiceImpl implements BatteryService {

    private final PowerSource battery_utils;


    private final WebClient webClient;

    /**
     * Instantiates a new Cpu service.
     *
     * @param webClientBuilder the web client builder
     */
    public BatteryServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
        battery_utils = new SystemInfo().getHardware().getPowerSources().get(0);
    }

    @Override
    public Mono<String> monitorBatteryInfo() {
        return Mono.just(buildBatteryInfo())
                .flatMap(this::sendBatteryInfo);
    }

    private Battery buildBatteryInfo() {
        return Battery.builder()
                .machineId(getMachineId())
                .timeStamp(getDatetime())
                .amperage(String.valueOf(battery_utils.getAmperage()))
                .chargePercent(String.valueOf(battery_utils.getRemainingCapacityPercent()))
                .dischargeTime(String.valueOf(battery_utils.getTimeRemainingEstimated()))
                .plugged(String.valueOf(battery_utils.isCharging()))
                .chargeCycles(String.valueOf(battery_utils.getCycleCount()))
                .temperature(String.valueOf(battery_utils.getTemperature()))
                .build();
    }

    private Mono<String> sendBatteryInfo(Battery batteryInfo) {
        log.info("URL: " + BATTERY_URL);
        log.info("Sending Battery... " + batteryInfo);
        return webClient.post()
                .uri(BATTERY_URL)
                .headers(h -> h.addAll(buildHttpHeaders("BATTERY")))
                .bodyValue(batteryInfo)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, e ->
                        Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ERROR - Client error while posting Battery Info. Please, make sure you have registered this machine with the ID " + getMachineId())))
                .onStatus(HttpStatus::is5xxServerError, e ->
                        Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR - Server error while posting Battery Info")))
                .bodyToMono(String.class);
    }
}
