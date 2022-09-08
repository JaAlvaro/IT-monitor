package com.monitor.app.model;

import lombok.Builder;

/**
 * @param id              the id
 * @param name            the model
 * @param type            the type
 * @param registerDate   the register date
 * @param lastConnection the last connection
 * @param screenConsent  the screen consent
 */
@Builder
public record Machine(String id,
                      String name,
                      String type,
                      String registerDate,
                      String lastConnection,
                      boolean screenConsent) {

}
