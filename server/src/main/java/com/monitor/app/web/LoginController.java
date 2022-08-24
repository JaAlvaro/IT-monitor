package com.monitor.app.web;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {

    @GetMapping({"/login"})
    public Mono<String> login(Model model) {

        model.addAttribute("titulo", "Inicio de sesión");

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> ctxt.getAuthentication().isAuthenticated() ? "redirect:home" : "login");
    }
}
