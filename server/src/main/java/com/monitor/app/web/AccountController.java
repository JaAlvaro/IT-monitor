package com.monitor.app.web;

import com.monitor.app.model.User;
import com.monitor.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/**
 * The type Account controller.
 */
@Controller
public class AccountController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Account mono.
     *
     * @param model the model
     * @return the mono
     */
    @GetMapping({"/account"})
    public Mono<String> account(Model model) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<User> user = userService.find(username)
                            .map(usr -> User.builder()
                                    .name(usr.name())
                                    .register_date(usr.register_date())
                                    .build());
                    model.addAttribute("user", user);

                    return "account";
                });
    }

    /**
     * Account mono.
     *
     * @param model the model
     * @return the mono
     */
    @GetMapping({"/account/change-password"})
    public Mono<String> changePass(Model model) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<User> user = userService.find(username)
                            .map(usr -> User.builder()
                                    .name(usr.name())
                                    .register_date(usr.register_date())
                                    .build());
                    model.addAttribute("user", user);

                    return "change-password";
                });
    }

    /**
     * Account mono.
     *
     * @param model the model
     * @return the mono
     */
    @GetMapping({"/account/delete-user"})
    public Mono<String> delete(Model model) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctxt -> {
                    var username = ctxt.getAuthentication().getName();
                    model.addAttribute("username", username);

                    Mono<User> user = userService.find(username)
                            .map(usr -> User.builder()
                                    .name(usr.name())
                                    .register_date(usr.register_date())
                                    .build());
                    model.addAttribute("user", user);

                    return "delete-user";
                });
    }
}

