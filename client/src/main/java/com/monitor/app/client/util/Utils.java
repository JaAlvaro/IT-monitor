package com.monitor.app.client.util;

import org.springframework.http.HttpHeaders;

public class Utils {

    public static HttpHeaders buildHttpHeaders(String channel){
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("Machine-id", getMachineId());
        httpHeaders.add("Channel", channel);
        return httpHeaders;
    }

    private static String getMachineId(){
        return "id-";
    }
}
