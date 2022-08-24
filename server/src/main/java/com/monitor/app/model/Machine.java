package com.monitor.app.model;

import lombok.Builder;

/**
 * @param machineId      the machine id
 * @param name           the name
 * @param type           the type
 * @param register_date  the register date
 * @param last_timestamp the time stamp
 */
@Builder
public record Machine(String machineId,
                      String name,
                      String type,
                      String register_date,
                      String last_timestamp) {

}
