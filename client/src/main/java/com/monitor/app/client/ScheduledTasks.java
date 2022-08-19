package com.monitor.app.client;

import com.monitor.app.client.service.impl.CpuServiceImpl;
import com.monitor.app.client.service.impl.OsServiceImpl;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Fan;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * The type Scheduled tasks.
 */
@Component
@Slf4j
public class ScheduledTasks {

    private final CpuServiceImpl cpuService = new CpuServiceImpl(WebClient.builder());
    private final OsServiceImpl osService = new OsServiceImpl(WebClient.builder());

    /**
     * Monitor info.
     */
    @Scheduled(fixedRate = 60000)
    public void monitorInfo() {
        cpuService.monitorCpuInfo().doOnNext(log::info).subscribe();

        osService.monitorOsInfo().doOnNext(log::info).subscribe();
    }


    private static void testing(){

    }

}