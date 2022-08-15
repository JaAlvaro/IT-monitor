package com.monitor.app.model;


import lombok.Builder;

/**
 * @param machineId the machine id
 * @param userId the user id
 */
@Builder
public record Machine(String machineId,
                      String userId) {

}
