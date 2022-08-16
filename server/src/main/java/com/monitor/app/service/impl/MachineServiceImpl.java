package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.service.MachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The type Machine service.
 */
@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    Collection<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT ID FROM machine");

    /*
    private final List<Machine> machineRepo = List.of(
            new Machine("DBE2C1100314005DC830A1", "JaAlvaro"),
            new Machine("PKWLF018JEW2PZ", "JaAlvaro"));
*/
    @Override
    public boolean checkId(String id) {

        return rows.stream()
                .map(Machine::machineId)
                .anyMatch(machineId -> machineId.equals(id));
    }
}

