package com.hotelbooking.hotelbooking.Config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotelbooking.hotelbooking.Scurity.CustomScurity;
import com.hotelbooking.hotelbooking.Service.SessionService;

@Configuration
public class ScurityConfig {
    @Bean
    public FilterRegistrationBean<CustomScurity> securityFilter(SessionService sessionService) {
        FilterRegistrationBean<CustomScurity> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomScurity(sessionService));
        registrationBean.addUrlPatterns("/*"); 
        return registrationBean;
    }
}
