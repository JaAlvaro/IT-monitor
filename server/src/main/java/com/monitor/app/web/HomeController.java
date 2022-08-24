package com.monitor.app.web;

import com.monitor.app.model.Machine;
import com.monitor.app.service.impl.MachineServiceImpl;
import com.monitor.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MachineServiceImpl machineService;

    @GetMapping({"/", "/home"})
    public Mono<String> home(Model model) {
        model.addAttribute("titulo", "Inicio");

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Flux<Machine> machines = machineService.findMachinesByUser(username);
                    //machines.switchIfEmpty(Mono.just(""));
                    // TODO if empty, register some one
                    model.addAttribute("machines", machines.subscribe());

                    return "home";
                });
    }

    // categorias : cpu, ram, os... en monitor.html. Aquí, listar machines y su laststamp

    // TODO actualizar batch sql last_timestamp con cada cpu insert
    // añadir nombre custom a machine en reigstro
}
