package com.monitor.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

/**
 * The type Server application.
 */
@SpringBootApplication(exclude = R2dbcAutoConfiguration.class)
public class ServerApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        /*
        httpSecurity
                .authorizeRequests()
                .antMatchers( "/favicon.ico").permitAll()*/
    }
}
