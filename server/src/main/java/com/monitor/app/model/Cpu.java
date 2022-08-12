package com.monitor.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class Cpu {

    /**
     * The machine id
     */
    private String machineId;

    /**
     * The time stamp
     */
    private String timeStamp;

    /**
     * The name
     */
    private String name;

    /**
     * The microarquitecture
     */
    private String microarchitecture;

    /**
     * The number of logical cores
     */
    private String logicalCores;

    /**
     * The number of physical cores
     */
    private String physicalCores;

    /**
     * The maximum frequency
     */
    private String maxFrequency;

    /**
     * The temperature
     */
    private String temperature;

    /**
     * The usage overload
     */
    private String overload;
}

