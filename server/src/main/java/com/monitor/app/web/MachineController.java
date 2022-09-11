package com.monitor.app.web;

import com.monitor.app.model.*;
import com.monitor.app.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@Slf4j
public class MachineController {

    @Autowired
    private MachineServiceImpl machineService;

    @Autowired
    private CpuServiceImpl cpuService;

    @Autowired
    private OsServiceImpl osService;

    @Autowired
    private ProgramServiceImpl programService;

    @Autowired
    private BatteryServiceImpl batteryService;

    @GetMapping({"machines"})
    public Mono<String> machines(Model model) {
        model.addAttribute("titulo", "Equipos");

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Flux<Machine> machines = machineService.findMachinesByUser(username);

                    model.addAttribute("machines", machines);

                    return "machines";
                });
    }

    @GetMapping({"machine/{id}"})
    public Mono<String> machine(Model model, @PathVariable String id) {

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctxt -> machineService.checkOwnership(id, ctxt.getAuthentication().getName())
                        .flatMap(isValid -> isValid ? Mono.just(ctxt) : Mono.empty()))
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<Machine> machine = machineService.find(id);
                    model.addAttribute("machine", machine);

                    return "machine";
                })
                .switchIfEmpty(Mono.just("redirect:/home?error"));
    }

    @GetMapping({"cpu/{id}"})
    public Mono<String> cpu(Model model, @PathVariable String id) {

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctxt -> machineService.checkOwnership(id, ctxt.getAuthentication().getName())
                        .flatMap(isValid -> isValid ? Mono.just(ctxt) : Mono.empty()))
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<Machine> machine = machineService.find(id);
                    model.addAttribute("machine", machine);

                    Flux<Cpu> cpu = cpuService.find(id);
                    model.addAttribute("measures", cpu);

                    return "cpu";
                })
                .switchIfEmpty(Mono.just("redirect:/home?error"));
    }

    @GetMapping({"os/{id}"})
    public Mono<String> os(Model model, @PathVariable String id) {

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctxt -> machineService.checkOwnership(id, ctxt.getAuthentication().getName())
                        .flatMap(isValid -> isValid ? Mono.just(ctxt) : Mono.empty()))
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<Machine> machine = machineService.find(id);
                    model.addAttribute("machine", machine);

                    Mono<Os> os = osService.find(id);
                    model.addAttribute("os", os);

                    Flux<Program> programs = programService.find(id);
                    model.addAttribute("programs", programs);

                    return "os";
                })
                .switchIfEmpty(Mono.just("redirect:/home?error"));
    }

    @GetMapping({"battery/{id}"})
    public Mono<String> battery(Model model, @PathVariable String id) {

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctxt -> machineService.checkOwnership(id, ctxt.getAuthentication().getName())
                        .flatMap(isValid -> isValid ? Mono.just(ctxt) : Mono.empty()))
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<Machine> machine = machineService.find(id);
                    model.addAttribute("machine", machine);

                    Flux<Battery> battery = batteryService.find(id);
                    model.addAttribute("measures", battery);

                    return "battery";
                })
                .switchIfEmpty(Mono.just("redirect:/home?error"));
    }

    @GetMapping({"control/{id}"})
    public Mono<String> control(Model model, @PathVariable String id) {

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(ctxt -> machineService.checkOwnership(id, ctxt.getAuthentication().getName())
                        .flatMap(isValid -> isValid ? Mono.just(ctxt) : Mono.empty()))
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<Machine> machine = machineService.find(id);
                    model.addAttribute("machine", machine);

                    //Flux<RemoteControl> control = remoteControlService.find(id);
                    var control = Flux.just(List.of());
                    model.addAttribute("commands", control);

                    return "control";
                })
                .switchIfEmpty(Mono.just("redirect:/home?error"));
    }
}
