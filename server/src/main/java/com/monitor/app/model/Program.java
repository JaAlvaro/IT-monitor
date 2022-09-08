package com.monitor.app.model;

import lombok.Builder;

/**
 * @param machineId the machine id
 * @param timeStamp the time stamp
 * @param name      the model
 */
@Builder
public record Program(String machineId,
                      String timeStamp,
                      String name) {

}