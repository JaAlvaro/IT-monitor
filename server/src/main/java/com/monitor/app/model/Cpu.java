package com.monitor.app.model;

/**
 * @param machineId         The machine id
 * @param timeStamp         The time stamp
 * @param name              The name
 * @param microarchitecture The microarquitecture
 * @param logicalCores      The number of logical cores
 * @param physicalCores     The number of physical cores
 * @param temperature       The temperature
 * @param load              The usage load
 */
public record Cpu(String machineId,
                  String timeStamp,
                  String name,
                  String microarchitecture,
                  String logicalCores,
                  String physicalCores,
                  String temperature,
                  String load) {

}

