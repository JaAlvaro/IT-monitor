package com.monitor.app.web;

import com.monitor.app.handler.CpuHandler;
import com.monitor.app.handler.OsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionsConfig {

    @Bean
    public RouterFunction<ServerResponse> cpuRoutes(CpuHandler handler){
        return route(POST("/v1/cpu"), handler::handleCpuPostRequest);
        /*
        return route(GET("/api/v1/productos"), handler::listar)
                .andRoute(GET("/api/v2/productos/{id}"), handler::ver)
                .andRoute(POST("/api/v2/productos"), handler::crear)
                .andRoute(PUT("/api/v2/productos/{id}"), handler::editar)
                .andRoute(DELETE("/api/v2/productos/{id}"), handler::eliminar)
                .andRoute(POST("/api/v2/productos/upload/{id}"), handler::upload);
         */
    }

    @Bean
    public RouterFunction<ServerResponse> osRoutes(OsHandler handler){
        return route(POST("/v1/os"), handler::handleOsPostRequest);
    }
}
