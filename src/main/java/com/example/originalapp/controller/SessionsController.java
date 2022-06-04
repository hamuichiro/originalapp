package com.example.originalapp.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionsController {

	@RequestMapping("/login")
    public String index() {
        return "session/login";
    }
}