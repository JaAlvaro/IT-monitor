package com.monitor.app.model;

/**
 * @param machineId the machine id
 * @param register_date the register date
 * @param timeStamp the time stamp
 */
public record Machine(String machineId,
                      String register_date,
                      String timeStamp) {

}
