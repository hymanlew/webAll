package com.hyman.security;

import org.springframework.security.web.PortResolver;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DefaultSavedRequest implements SavedRequest{

    private String requestURI;

    public DefaultSavedRequest(HttpServletRequest request, PortResolver portResolver) {
    }

    @Override
    public String getRedirectUrl() {
        return null;
    }

    @Override
    public List<Cookie> getCookies() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public List<String> getHeaderValues(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public List<Locale> getLocales() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public boolean doesRequestMatch(HttpServletRequest request, PortResolver portResolver) {
        return true;
    }
}
