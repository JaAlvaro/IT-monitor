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

    private CpuServiceImpl cpuService = new CpuServiceImpl(WebClient.builder());
    private OsServiceImpl osService = new OsServiceImpl();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void monitorInfo() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info(cpuService.monitorCpuInfo().toString());
        //log.info(osService.monitorOsInfo().toString());
    }


}