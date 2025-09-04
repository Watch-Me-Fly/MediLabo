package com.oc.frontend.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Value("${gateway.login-url}")
    private String loginUrl;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginUrl", loginUrl);
        return "login";
    }
}
