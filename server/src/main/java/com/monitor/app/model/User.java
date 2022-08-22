package com.monitor.app.model;

import lombok.Builder;

/**
 * @param name          the password
 * @param register_date the register date
 * @param password      the password
 */
@Builder
public record User(String name,
                   String password,
                   String register_date) {

}
