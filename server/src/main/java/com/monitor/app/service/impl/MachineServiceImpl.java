package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.service.MachineService;
import com.monitor.app.util.Util;
import dev.miku.r2dbc.mysql.MySqlConnection;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.ZoneId;
import java.util.List;

/**
 * The type Machine service.
 */
@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    @Override
    public Mono<Boolean> checkId(String id) {

        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT ID FROM machine WHERE ID = '" + id + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> row.get("id", String.class)))
                .next()
                .map(row -> row.equals(id));
    }


}

