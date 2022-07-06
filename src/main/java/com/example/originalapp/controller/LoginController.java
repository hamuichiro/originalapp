package com.example.originalapp.controller;


import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index() {
        return "sessions/login";
    }
    
	@RequestMapping("/users/new")
    public String user() {
        return "/users/new";
    }
	
    @RequestMapping("/index")
    public String login() {  
    	 return "pages/index";
    }
    
    @RequestMapping("/complete")
    public String loginComplete() {  
    	 return "sessions/complete";
    }


    
	@GetMapping(path = "/failure")
    public String loginFailure(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-danger");
        model.addAttribute("message", "Nameまたはパスワードに誤りがあります。");

        return "sessions/failure";
    }

    @GetMapping(path = "/logout")
    public String logoutComplete(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-info");
        model.addAttribute("message", "ログアウトしました。");

        return "sessions/logout";
    }
}