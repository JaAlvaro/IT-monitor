package com.monitor.app.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.monitor.app.client.service.impl.CpuServiceImpl;
import com.monitor.app.client.service.impl.OsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;

@Component
@Slf4j
public class ScheduledTasks {

    private final CpuServiceImpl cpuService = new CpuServiceImpl(WebClient.builder());
    private final OsServiceImpl osService = new OsServiceImpl();

    @Scheduled(fixedRate = 60000)
    public void monitorInfo() {
        cpuService.monitorCpuInfo()
                .doOnNext(log::info)
                .subscribe();
        //log.info(osService.monitorOsInfo().toString());
    }


}