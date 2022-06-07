package com.example.originalapp.controller;


import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionsController {

    @GetMapping(path = "/login")
    public String index() {
        return "sessions/new";
    }
    
	@RequestMapping("/users/new")
    public String user() {
        return "users/new";
    }

    @GetMapping(path = "/login-failure")
    public String loginFailure(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-danger");
        model.addAttribute("message", "Nameまたはパスワードに誤りがあります。");

        return "sessions/new";
    }

    @GetMapping(path = "/logout-complete")
    public String logoutComplete(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-info");
        model.addAttribute("message", "ログアウトしました。");

        return "layouts/complete";
    }
}