package com.monitor.app.client.model;

import lombok.Builder;

/**
 * @param machineId    The machine id
 * @param timeStamp    The time stamp
 * @param family       The os type
 * @param version      The os name
 * @param manufacturer The manufacturer
 * @param user         The user logged
 * @param hostname     The hostname
 * @param bitness      The bitness
 */
@Builder
public record Os(String machineId,
                 String timeStamp,
                 String family,
                 String version,
                 String manufacturer,
                 String user,
                 String hostname,
                 String bitness) {

}
