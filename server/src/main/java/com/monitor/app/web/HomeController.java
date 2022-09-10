package com.monitor.app.web;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {


    @GetMapping({"/", "/home"})
    public Mono<String> home(Model model) {
        model.addAttribute("titulo", "Inicio");

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    return "home";
                });
    }


    // categorias : cpu, ram, os... en monitor.html. Aquí, listar machines y su laststamp

    //Crear llamada de get consent
    //SCREEN_CONSENT yes default en MACHINE o en única instancia de pantalla

}
