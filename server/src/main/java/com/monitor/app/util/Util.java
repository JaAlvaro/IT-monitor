package com.monitor.app.util;

import dev.miku.r2dbc.mysql.MySqlConnection;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import reactor.core.publisher.Mono;

import java.time.ZoneId;

/**
 * The type Util.
 */
public class Util {

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public static Mono<MySqlConnection> getConnection() {
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host("localhost")
                .user("root")
                .password("IT-Monitor.UC3M!")
                .database("ITMONITOR")
                .serverZoneId(ZoneId.of("Europe/Paris"))
                .build();

        return Mono.from(MySqlConnectionFactory.from(configuration).create());
    }
}
