package com.monitor.app.client.service.impl;

import com.monitor.app.client.model.ProgramList;
import com.monitor.app.client.service.ProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static com.monitor.app.client.util.Constants.programUrl;
import static com.monitor.app.client.util.Utils.*;

@Service
@Slf4j
public class ProgramServiceImpl implements ProgramService {

    private final WebClient webClient;

    public ProgramServiceImpl(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.build();
    }

    @Override
    public Mono<String> monitorProgramList() {
        return Mono.just(buildProgramList())
                .flatMap(this::sendProgramList);
    }

    private ProgramList buildProgramList() {
        return ProgramList.builder()
                .machineId(getMachineId())
                .timeStamp(getDatetime())
                .nameList(getInstalledPrograms())
                .build();
    }

    private List<String> getInstalledPrograms() {
        try {
            //.exec("powershell -command \"Get-ItemProperty HKLM:\\Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\* | Select-Object DisplayName \"")
            return getCmdOutputList("wmic product get name", "Name");
        } catch (IOException e) {
            log.error("Error while obtaining list of installed programs", e);
            return List.of();
        }
    }

    private Mono<String> sendProgramList(ProgramList programList) {
        log.info("URL: " + programUrl);
        log.info("Sending Programs... " + programList);
        return webClient.post()
                .uri(programUrl)
                .headers(h -> h.addAll(buildHttpHeaders("Programs")))
                .bodyValue(programList)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, e ->
                        Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ERROR - Client error while posting Programs Info. Please, make sure you have registered this machine with the ID " + getMachineId())))
                .onStatus(HttpStatus::is5xxServerError, e ->
                        Mono.error(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR - Server error while posting Programs Info")))
                .bodyToMono(String.class);
    }
}
