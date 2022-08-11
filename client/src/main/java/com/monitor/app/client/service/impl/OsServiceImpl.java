package com.monitor.app.client.service.impl;

import com.monitor.app.client.model.Os;
import com.monitor.app.client.service.OsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class OsServiceImpl implements OsService {

    private final OperatingSystem os_utils;

    public OsServiceImpl() {
        this.os_utils = new SystemInfo().getOperatingSystem();
    }

    @Override
    public Os getOsInfo() {
        return Os.builder()
                .family(os_utils.getFamily())
                .version(os_utils.getVersionInfo().toString())
                .manufacturer(os_utils.getManufacturer())
                .processCount(String.valueOf(os_utils.getProcessCount()))
                .threadCount(String.valueOf(os_utils.getThreadCount()))
                .bootTime(String.valueOf(os_utils.getSystemBootTime()))
                .upTime(String.valueOf(os_utils.getSystemUptime()))
                .hostname(os_utils.getNetworkParams().getHostName())
                .user(System.getProperty("user.name"))
                .bitness(String.valueOf(os_utils.getBitness()))
                .programs(getInstalledPrograms())
                .build();
    }

    private List<String> getInstalledPrograms() {
        try {
            return new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                    //.exec("powershell -command \"Get-ItemProperty HKLM:\\Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\* | Select-Object DisplayName \"")
                    .exec("wmic product get name")
                    .getInputStream())).lines()
                    .filter(s -> !(s.contains("Name") ||
                            !(s.contains("a") || s.contains("e") || s.contains("i") || s.contains("o") || s.contains("u"))))
                    //.peek(System.out::println)
                    .map(line -> line.replace("  ", ""))
                    .toList();
        } catch (IOException e) {
            log.error("Error while obtaining list of installed programs", e );
            return List.of();
        }
    }
}
