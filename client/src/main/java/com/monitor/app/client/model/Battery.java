package com.monitor.app.client.model;

import lombok.Builder;

/**
 * @param machineId     the machine id
 * @param timeStamp     the time stamp
 * @param amperage      the amperage
 * @param plugged       the plugged
 * @param chargePercent the charge percent
 * @param dischargeTime the discharge time
 * @param chargeCycles  the charge cycles
 * @param temperature   the temperature
 */
@Builder
public record Battery(String machineId,
                      String timeStamp,
                      String amperage,
                      String plugged,
                      String chargePercent,
                      String dischargeTime,
                      String chargeCycles,
                      String temperature) {

}