package com.monitor.app.web;

import com.monitor.app.model.User;
import com.monitor.app.service.impl.MachineServiceImpl;
import com.monitor.app.service.impl.UserServiceImpl;
import com.monitor.app.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class RegisterController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MachineServiceImpl machineService;

    @GetMapping({"/register/user"})
    public Mono<String> register(Model model) {

        //Flux<Producto> productos = service.findAllConNombreUpperCase();

        //productos.subscribe(prod -> log.info(prod.getNombre()));

        //model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Registro de usuario");
        return Mono.just("register");
    }

    @PostMapping({"/register/user"})
    public Mono<String> register(User user, Model model) {

        return Mono.just(user.name())
                .flatMap(name -> userService.checkUser(name).flatMap(exists -> exists ? Mono.empty() : Mono.just(name)))
                .flatMap(usr -> userService.insert(new User(user.name(), user.password(), Util.getDatetime())))
                .map(result -> {
                    model.addAttribute("titulo", "Login post-register");
                    return "login";
                })
                .switchIfEmpty(Mono.just("redirect:/register/user?error"));
    }


}
