package com.monitor.app.client.service.impl;

import com.monitor.app.client.model.Cpu;
import com.monitor.app.client.service.CpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.monitor.app.client.util.Constant.CPU_URL;
import static com.monitor.app.client.util.Util.*;

/**
 * The type Cpu service.
 */
@Service
@Slf4j
public class CpuServiceImpl implements CpuService {

    private final CentralProcessor cpu_utils;

    private final Sensors sensor_utils;

    private final WebClient webClient;

    /**
     * Instantiates a new Cpu service.
     *
     * @param webClientBuilder the web client builder
     */
    public CpuServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
        cpu_utils = new SystemInfo().getHardware().getProcessor();
        sensor_utils = new SystemInfo().getHardware().getSensors();
    }

    @Override
    public Mono<String> monitorCpuInfo() {
        return Mono.just(buildCpuInfo())
                .flatMap(this::sendCpuInfo);
    }

    private Cpu buildCpuInfo() {
        // TODO probar sensor profesorfalken/jSensors (GPU,...)
        return Cpu.builder()
                .machineId(getMachineId())
                .timeStamp(getDatetime())
                .model(cpu_utils.getProcessorIdentifier().getName())
                .microarchitecture(cpu_utils.getProcessorIdentifier().getMicroarchitecture())
                .physicalCores(String.valueOf(cpu_utils.getPhysicalProcessorCount()))
                .logicalCores(String.valueOf(cpu_utils.getLogicalProcessorCount()))
                .temperature(String.valueOf(sensor_utils.getCpuTemperature()))
                .load(getCpuLoad())
                .build();
    }

    private Mono<String> sendCpuInfo(Cpu cpuInfo) {
        log.info("URL: " + CPU_URL);
        log.info("Sending Cpu... " + cpuInfo);
        return webClient.post()
                .uri(CPU_URL)
                .headers(h -> h.addAll(buildHttpHeaders("CPU")))
                .bodyValue(cpuInfo)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, e ->
                        Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ERROR - Client error while posting CPU Info. Please, make sure you have registered this machine with the ID " + getMachineId())))
                .onStatus(HttpStatus::is5xxServerError, e ->
                        Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR - Server error while posting CPU Info")))
                .bodyToMono(String.class);
    }

    private String getCpuLoad() {
        try {
            return getCmdOutputLine("wmic cpu get loadpercentage", "Load") + "%";
        } catch (IOException e) {
            log.error("Cpu load cannot be obtained.");
            return "0%";
        }
    }
}
