package com.monitor.app.service;

import com.monitor.app.model.User;
import reactor.core.publisher.Mono;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Insert mono.
     *
     * @param user the user
     * @return the mono
     */
    Mono<String> insert(User user);

    /**
     * Find mono.
     *
     * @param username the username
     * @return the mono
     */
    Mono<User> find(String username);

    /**
     * Delete mono.
     *
     * @param machineId the machine id
     * @return the mono
     */
    Mono<Void> delete(String machineId);

    /**
     * Check user mono.
     *
     * @param name the name
     * @return the mono
     */
    Mono<Boolean> checkUser(String name);

    /**
     * Update password mono.
     *
     * @param username the username
     * @param password the password
     * @return the mono
     */
    Mono<String> updatePassword(String username, String password);
}
