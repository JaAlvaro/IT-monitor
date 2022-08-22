package com.monitor.app.model;

import lombok.Builder;

/**
 * @param machineId the machine id
 * @param register_date the register date
 * @param timeStamp the time stamp
 */
@Builder
public record Machine(String machineId,
                      String register_date,
                      String timeStamp) {

}
