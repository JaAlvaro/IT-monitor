package com.monitor.app.service.impl;

import com.monitor.app.model.Battery;
import com.monitor.app.service.BatteryService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class BatteryServiceImpl implements BatteryService {

    @Override
    @PreAuthorize("hasRole('MONITOR')")
    public Mono<String> insert(Battery battery) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement(format("INSERT INTO BATTERY VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                battery.machineId(), battery.timeStamp(), battery.amperage(), battery.plugged(), battery.chargePercent(),
                                battery.dischargeTime(), battery.chargeCycles(), battery.temperature()))
                        .execute()).next()
                .map(insertion -> {
                    log.info("SUCCESS - Saved Battery: " + battery);
                    return "SUCCESS - Battery received and saved";
                });
    }

    @Override
    public Flux<Battery> find(String machineId) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM battery WHERE MACHINE_ID = '" + machineId + "' ORDER BY TIMESTAMP DESC LIMIT 20").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> Battery.builder()
                        .machineId(machineId)
                        .timeStamp(row.get("timestamp", String.class))
                        .plugged(row.get("plugged", String.class))
                        .amperage(row.get("amperage", String.class))
                        .chargeCycles(row.get("charge_cycles", String.class))
                        .chargePercent(row.get("charge_percent", String.class))
                        .dischargeTime(row.get("discharge_time", String.class))
                        .dischargeTime(row.get("temperature", String.class))
                        .build()));
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }

}
