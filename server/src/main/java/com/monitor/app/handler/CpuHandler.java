package com.monitor.app.handler;

import com.monitor.app.services.impl.CpuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CpuHandler {

    @Autowired
    private CpuServiceImpl service;

}
