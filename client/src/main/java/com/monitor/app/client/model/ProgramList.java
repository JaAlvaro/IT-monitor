package com.monitor.app.client.model;

import lombok.Builder;

import java.util.List;

/**
 * @param machineId the machine id
 * @param timeStamp the time stamp
 * @param nameList  the name list
 */
@Builder
public record ProgramList(String machineId,
                          String timeStamp,
                          List<String> nameList) {

}