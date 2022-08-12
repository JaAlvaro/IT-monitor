package com.monitor.app.client.util;

import org.springframework.http.HttpHeaders;

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
        httpHeaders.add("Machine-id", getMachineId());
        httpHeaders.add("Channel", channel);
        return httpHeaders;
    }

    /**
     * Gets machine id.
     *
     * @return the machine id
     */
    public static String getMachineId() {
        //wmic bios get serialnumber
        try {
            return new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                    .exec("wmic baseboard get serialnumber")
                    .getInputStream())).lines()
                    .reduce((header, value) -> value)
                    .map(String::trim)
                    .orElse("");
        } catch (IOException e) {
            return "";
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
