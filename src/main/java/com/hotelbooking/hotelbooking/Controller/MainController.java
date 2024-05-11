package com.hotelbooking.hotelbooking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hotelbooking.hotelbooking.Entity.Account;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class MainController {
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @GetMapping("/forgotPass")
    public String repass() {
        return "repass.html";
    }

    @GetMapping("/index")
    public String index() {
        return "/User_UI/index.html";
    }

    @GetMapping("/tours")
    public String tour() {
        return "/User_UI/tours.html";
    }

    @GetMapping("/hotels")
    public String hotel(){
        return "/User_UI/hotels.html";
    }

    @GetMapping("/contact")
    public String contact(){
        return "/User_UI/contact.html";
    }

    @GetMapping("/about")
    public String about(){
        return "/User_UI/about.html";
    }

    @GetMapping("/blog")
    public String blog(){
        return "/User_UI/blog.html";
    }

    @GetMapping("/services")
    public String services(){
        return "/User_UI/services.html";
    }

    @GetMapping("/profile")
    public String showProfile(HttpServletRequest request, Model model) {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            model.addAttribute("account", account);
            return "profile"; // Render profile.html
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/test")
    public String test(){
        return "testMap.html";
    }
}
