package com.monitor.app.service.impl;

import com.monitor.app.service.MachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    //TODO JDBC
    private final List<String> machineRepo = List.of("abc", "PKWLF018JEW2PZ");

    @Override
    public boolean checkId(String id) {
        return machineRepo.contains(id);
    }
}
