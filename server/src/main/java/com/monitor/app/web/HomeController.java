package com.monitor.app.web;

import com.monitor.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping({"/", "/home"})
    public Mono<String> home(Model model) {
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("username", "Álvaro");
        return Mono.just("home");
    }
}
