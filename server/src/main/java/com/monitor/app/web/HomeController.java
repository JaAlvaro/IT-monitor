package com.monitor.app.web;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * The type Home controller.
 */
@Controller
public class HomeController {

    /**
     * Home mono.
     *
     * @param model the model
     * @return the mono
     */
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

    /**
     * Cookies mono.
     *
     * @param model the model
     * @return the mono
     */
    @GetMapping({"/cookies"})
    public Mono<String> cookies(Model model) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    return "cookies";
                });
    }

    /**
     * Privacy mono.
     *
     * @param model the model
     * @return the mono
     */
    @GetMapping({"/privacy"})
    public Mono<String> privacy(Model model) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    return "privacy";
                });
    }

    /**
     * Legal mono.
     *
     * @param model the model
     * @return the mono
     */
    @GetMapping({"/legal"})
    public Mono<String> legal(Model model) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    return "legal";
                });
    }
}
