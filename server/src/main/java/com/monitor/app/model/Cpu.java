package com.monitor.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Cpu(@JsonProperty("machineId") String machineId,
                  @JsonProperty("timeStamp") String timeStamp,
                  @JsonProperty("name") String name,
                  @JsonProperty("microarchitecture") String microarchitecture,
                  @JsonProperty("logicalCores") String logicalCores,
                  @JsonProperty("physicalCores") String physicalCores,
                  @JsonProperty("temperature") String temperature,
                  @JsonProperty("load") String load) {

}

