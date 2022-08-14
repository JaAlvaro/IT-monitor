package com.monitor.app.client.model;

import lombok.Builder;

import java.util.List;

/**
 * @param machineId    The machine id
 * @param timeStamp    The time stamp
 * @param family       The os type
 * @param version      The os name
 * @param manufacturer The manufacturer
 * @param user         The user logged
 * @param processCount The process count
 * @param threadCount  The thread count
 * @param bootTime     The boot time
 * @param upTime       The uptime
 * @param hostname     The hostname
 * @param bitness      The bitness
 * @param programs     The list of programs
 */
@Builder
public record Os(String machineId,
                 String timeStamp,
                 String family,
                 String version,
                 String manufacturer,
                 String user,
                 String processCount,
                 String threadCount,
                 String bootTime,
                 String upTime,
                 String hostname,
                 String bitness,
                 List<String> programs) {

}
