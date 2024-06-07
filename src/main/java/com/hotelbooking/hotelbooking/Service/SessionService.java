package com.hotelbooking.hotelbooking.Service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

    public void setSession(String key, Object value, HttpSession session){
        session.setAttribute(key, value);
    }

    public Object getSession(String key,HttpSession session){
        Object value = session.getAttribute(key);
        return value != null ? value : null;
    }

}
