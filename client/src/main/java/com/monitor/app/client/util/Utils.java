package com.monitor.app.client.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.monitor.app.client.util.Constants.datetimePattern;

/**
 * The type Utils.
 */
public class Utils {

    /**
     * Build http headers http headers.
     *
     * @param channel the channel
     * @return the http headers
     */
    public static HttpHeaders buildHttpHeaders(String channel) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Channel", channel);
        return httpHeaders;
    }

    /**
     * Gets machine id.
     *
     * @return the machine id
     */
    public static String getMachineId() {
        try {
            return new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                    ////wmic bios get serialnumber
                    .exec("wmic baseboard get serialnumber")
                    .getInputStream())).lines()
                    .filter(str -> !(str.contains("Serial") || str.isEmpty()))
                    .map(String::trim)
                    .toList().get(0);
        } catch (IOException e) {
            throw new NullPointerException("ERROR: Machine ID cannot be obtained.");
        }
    }

    /**
     * Gets datetime.
     *
     * @return the datetime
     */
    public static String getDatetime() {
        return new SimpleDateFormat(datetimePattern).format(new Date());
    }
}
