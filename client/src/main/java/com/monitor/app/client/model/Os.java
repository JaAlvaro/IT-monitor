package com.monitor.app.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Builder
@AllArgsConstructor
@ToString
public class Os {

    /**
     * The machine id
     */
    private String machineId;

    /**
     * The time stamp
     */
    private String timeStamp;

    /**
     * The os type
     */
    private String family;

    /**
     * The os name
     */
    private String version;

    /**
     * The manufacturer
     */
    private String manufacturer;

    /**
     * The user logged
     */
    private String user;

    /**
     * The process count
     */
    private String processCount;

    /**
     * The thread count
     */
    private String threadCount;

    /**
     * The boot time
     */
    private String bootTime;

    /**
     * The up time
     */
    private String upTime;

    /**
     * The hostname
     */
    private String hostname;

    /**
     * The bitness
     */
    private String bitness;

    /**
     * The list of programs
     */
    private List<String> programs;
}
