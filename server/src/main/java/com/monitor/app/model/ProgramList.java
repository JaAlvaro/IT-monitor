package com.monitor.app.model;

import java.util.List;

/**
 * @param machineId the machine id
 * @param timeStamp the time stamp
 * @param nameList  the name
 */
public record ProgramList(String machineId,
                          String timeStamp,
                          List<String> nameList) {

}