package com.monitor.app.client.service.impl;

import com.monitor.app.client.model.Os;
import com.monitor.app.client.service.OsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;
import reactor.core.publisher.Mono;

import static com.monitor.app.client.util.Constant.OS_URL;
import static com.monitor.app.client.util.Util.*;

/**
 * The type Os service.
 */
@Service
@Slf4j
public class OsServiceImpl implements OsService {

    private final OperatingSystem os_utils = new SystemInfo().getOperatingSystem();

    private final WebClient webClient;

    public OsServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
    }

    @Override
    public Mono<String> monitorOsInfo() {
        return Mono.just(buildOsInfo())
                .flatMap(this::sendOsInfo);
    }

    private Os buildOsInfo() {
        return Os.builder()
                .machineId(getMachineId())
                .timeStamp(getDatetime())
                .family(os_utils.getFamily())
                .version(os_utils.getVersionInfo().toString())
                .manufacturer(os_utils.getManufacturer())
                .hostname(os_utils.getNetworkParams().getHostName())
                .user(System.getProperty("user.name"))
                .bitness(String.valueOf(os_utils.getBitness()))
                .build();
    }

    private Mono<String> sendOsInfo(Os osInfo) {
        log.info("URL: " + OS_URL);
        log.info("Sending Os... " + osInfo);
        return webClient.post()
                .uri(OS_URL)
                .headers(h -> h.addAll(buildHttpHeaders("OS")))
                .bodyValue(osInfo)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, e ->
                        Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ERROR - Client error while posting OS Info. Please, make sure you have registered this machine with the ID " + getMachineId())))
                .onStatus(HttpStatus::is5xxServerError, e ->
                        Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR - Server error while posting OS Info")))
                .bodyToMono(String.class);
    }
}
