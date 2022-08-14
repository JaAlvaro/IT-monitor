package com.monitor.app.client.service.impl;

import com.monitor.app.client.model.Cpu;
import com.monitor.app.client.service.CpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.monitor.app.client.util.Constants.cpuUrl;
import static com.monitor.app.client.util.Utils.*;

@Service
@Slf4j
public class CpuServiceImpl implements CpuService {

    private final CentralProcessor cpu_utils = new SystemInfo().getHardware().getProcessor();

    private final Sensors sensor_utils = new SystemInfo().getHardware().getSensors();

    private final WebClient webClient;

    public CpuServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
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
                .name(cpu_utils.getProcessorIdentifier().getName())
                .microarchitecture(cpu_utils.getProcessorIdentifier().getMicroarchitecture())
                .physicalCores(String.valueOf(cpu_utils.getPhysicalProcessorCount()))
                .logicalCores(String.valueOf(cpu_utils.getLogicalProcessorCount()))
                .temperature(String.valueOf(sensor_utils.getCpuTemperature()))
                .load(getCpuLoad())
                .build();
    }

    private Mono<String> sendCpuInfo(Cpu cpuInfo) {
        log.info("URL: " + cpuUrl);
        log.info("Sending Cpu... " + cpuInfo);
        return webClient.post()
                .uri(cpuUrl)
                .headers(h -> h.addAll(buildHttpHeaders("cpu")))
                .bodyValue(cpuInfo)
                .retrieve()
                .bodyToMono(String.class);
    }

    private String getCpuLoad() {
        try {
            return new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                    ////wmic bios get serialnumber
                    .exec("wmic cpu get loadpercentage")
                    .getInputStream())).lines()
                    .filter(str -> !(str.contains("Load") || str.isEmpty()))
                    .map(String::trim)
                    .map(str -> str.concat("%"))
                    .toList().get(0);
        } catch (IOException e) {
            log.error("Cpu load cannot be obtained.");
            return "0%";
        }
    }
}
