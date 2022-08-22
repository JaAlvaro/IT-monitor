package com.monitor.app.service.impl;

import com.monitor.app.model.Os;
import com.monitor.app.service.OsService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class OsServiceImpl implements OsService {

    @Override
    @PreAuthorize("hasRole('MONITOR')")
    public Mono<String> insert(Os os) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createBatch()
                        .add(format("SET @machine = '%s', @time = '%s', @family = '%s', @version = '%s', @manufacturer = '%s', @user = '%s', @hostname = '%s', @bitness = '%s'",
                                os.machineId(), os.timeStamp(), os.family(), os.version(), os.manufacturer(), os.user(), os.hostname(), os.bitness()))
                        .add("INSERT INTO OS VALUES (@machine, @time, @family, @version, @manufacturer, @user, @hostname, @bitness) ON DUPLICATE KEY UPDATE TIMESTAMP = @time, FAMILY = @family, VERSION = @version, MANUFACTURER = @manufacturer, USER = @user, HOSTNAME = @hostname, BITNESS = @bitness")
                        .execute())
                .next()
                .map(result -> {
                    log.info("SUCCESS - Saved OS: " + os);
                    return "SUCCESS - OS received and saved";
                });
    }

    @Override
    public Mono<Os> find(String machineId) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM machine WHERE ID = '" + machineId + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> Os.builder()
                        .machineId(machineId)
                        .timeStamp(row.get("timestamp", String.class))
                        .family(row.get("family", String.class))
                        .version(row.get("version", String.class))
                        .manufacturer(row.get("manufacturer", String.class))
                        .user(row.get("user", String.class))
                        .hostname(row.get("hostname", String.class))
                        .bitness(row.get("bitness", String.class))
                        .build()))
                .next();
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }

}
