package com.monitor.app.web;

import com.monitor.app.model.Machine;
import com.monitor.app.service.impl.MachineServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class MachineController {

    @Autowired
    private MachineServiceImpl machineService;

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
                    //todo devolver / si el machine no pertenece al usuario
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
                .switchIfEmpty(Mono.just("redirect:/home"));
    }
}
