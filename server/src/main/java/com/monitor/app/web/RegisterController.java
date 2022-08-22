package com.monitor.app.web;

import com.monitor.app.service.impl.MachineServiceImpl;
import com.monitor.app.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class RegisterController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MachineServiceImpl machineService;

    @GetMapping({"/register/user"})
    public Mono<String> registrar(Model model) {

        //Flux<Producto> productos = service.findAllConNombreUpperCase();

        //productos.subscribe(prod -> log.info(prod.getNombre()));

        //model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Registro de usuario");
        return Mono.just("register");
    }


}
