package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.service.MachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Machine service.
 */
@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    //TODO JDBC
    private final List<Machine> machineRepo = List.of(
            new Machine("DBE2C1100314005DC830A1", "JaAlvaro"),
            new Machine("PKWLF018JEW2PZ", "JaAlvaro"));

    @Override
    public boolean checkId(String id) {

        return machineRepo.stream()
                .map(Machine::machineId)
                .anyMatch(machineId -> machineId.equals(id));
    }
}
