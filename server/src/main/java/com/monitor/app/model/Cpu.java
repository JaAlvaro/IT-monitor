package com.monitor.app.model;

import lombok.Builder;

/**
 * @param machineId         The machine id
 * @param timeStamp         The time stamp
 * @param model             The model
 * @param microarchitecture The microarquitecture
 * @param logicalCores      The number of logical cores
 * @param physicalCores     The number of physical cores
 * @param temperature       The temperature
 * @param load              The usage load
 */
@Builder
public record Cpu(String machineId,
                  String timeStamp,
                  String model,
                  String microarchitecture,
                  String logicalCores,
                  String physicalCores,
                  String temperature,
                  String load) {

}

