package com.example.originalapp.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionsController {

    @GetMapping(path = "/login")
    public String index() {
        return "session/login";
    }
}