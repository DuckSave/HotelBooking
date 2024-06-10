package com.hotelbooking.hotelbooking.Scurity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Service.SessionService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomScurity implements Filter {

    private final SessionService sessionService;

    @Autowired
    public CustomScurity(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // Danh sách các URL không cần bảo vệ
    private static final List<String> EXCLUDED_URLS = Arrays.asList("/login", "/register");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (isExcludedUrl(uri, method)) {
            chain.doFilter(request, response);
            return;
        }

        Account account = (Account) sessionService.getSession("account", httpRequest.getSession());

        if (account != null) {
            String role = account.isRole() ? "ADMIN" : "USER";

            if (uri.startsWith("/admin") && !"ADMIN".equals(role)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            } 

        } else {

            httpResponse.sendRedirect("/login");
            return;
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }

    private boolean isExcludedUrl(String uri, String method) {

        boolean isExcluded = EXCLUDED_URLS.stream().anyMatch(uri::startsWith);


        if (isExcluded && "POST".equalsIgnoreCase(method)) {
            return true;
        }

        return isExcluded && "GET".equalsIgnoreCase(method);
    }
}
