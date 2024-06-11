package com.hotelbooking.hotelbooking.Controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LangugageController {
    private final LocaleResolver localeResolver;

    public LangugageController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }
    
    @GetMapping("/change-language")
    public String changeLanguage(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("lang") String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        localeResolver.setLocale(request, response, locale);
        return "redirect:/index";
    }
    
}
