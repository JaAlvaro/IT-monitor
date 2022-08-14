package com.monitor.app.client.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
            //wmic bios get serialnumber
            return getCmdOutputLine("wmic baseboard get serialnumber", "Serial");
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


    /**
     * Gets cmd output line.
     *
     * @param command the command
     * @param header  the header
     * @return the cmd output line
     * @throws IOException the io exception
     */
    public static String getCmdOutputLine(String command, String header) throws IOException {
        return getCmdOutputList(command, header).get(0);
    }

    /**
     * Gets cmd output list.
     *
     * @param command the command
     * @param header  the header
     * @return the cmd output list
     * @throws IOException the io exception
     */
    public static List<String> getCmdOutputList(String command, String header) throws IOException {
        return new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                .exec(command)
                .getInputStream())).lines()
                .filter(str -> !(str.contains(header) || str.isEmpty()))
                .map(String::trim)
                .toList();
    }
}
