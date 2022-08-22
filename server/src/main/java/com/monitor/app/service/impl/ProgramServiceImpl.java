package com.monitor.app.service.impl;

import com.monitor.app.model.Machine;
import com.monitor.app.model.Program;
import com.monitor.app.model.ProgramList;
import com.monitor.app.service.ProgramService;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@Slf4j
public class ProgramServiceImpl implements ProgramService {

    @Override
    @PreAuthorize("hasRole('MONITOR')")
    public Mono<String> insert(ProgramList programList) {

        return Util.getConnection()
                .flatMapMany(conn -> {
                    var batch = conn.createBatch();
                    programList.nameList()
                            .forEach(program -> batch.add(format("INSERT INTO PROGRAM VALUES ('%s', '%s', '%s') ON DUPLICATE KEY UPDATE TIMESTAMP = '%s'",
                                    programList.machineId(), programList.timeStamp(), program, programList.timeStamp())));
                    return batch.execute();
                }).next()
                .map(result -> {
                    log.info("SUCCESS - Saved Programs: " + programList);
                    return "SUCCESS - Programs received and saved";
                });
    }

    @Override
    public Flux<Program> find(String machineId) {
        return Util.getConnection()
                .flatMapMany(conn -> conn.createStatement("SELECT * FROM program WHERE MACHINE_ID = '" + machineId + "'").execute())
                .flatMap(mySqlResult -> mySqlResult.map((row, metadata) -> Program.builder()
                        .machineId(machineId)
                        .timeStamp(row.get("timestamp", String.class))
                        .name(row.get("name", String.class))
                        .build()));
    }

    @Override
    public Mono<Void> delete(String machineId) {
        return null;
    }
}
