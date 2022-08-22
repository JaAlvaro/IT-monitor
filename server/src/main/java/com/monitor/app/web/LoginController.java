package com.monitor.app.web;

import com.monitor.app.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping({"/login"})
    public Mono<String> login(Model model) {
        model.addAttribute("titulo", "Inicio de sesión");
        return Mono.just("login");
    }
}
