package com.hotelbooking.hotelbooking.Service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

    public void setSession(String key, String value, HttpSession session){
        session.setAttribute(key, value);
    }

    public String getSession(String key,HttpSession session){
        Object value = session.getAttribute(key);
        return value != null ? value.toString() : null;
    }

}
