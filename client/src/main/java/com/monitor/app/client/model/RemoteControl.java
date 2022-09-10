package com.monitor.app.client.model;

import lombok.Builder;

/**
 * @param machineId the machine id
 * @param timeStamp the time stamp
 * @param command   the command
 */
@Builder
public record RemoteControl(String machineId,
                            String timeStamp,
                            String command) {

}