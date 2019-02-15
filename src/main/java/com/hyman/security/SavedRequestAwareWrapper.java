package com.hyman.security;

import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 自定义的缓存包裹器，与 SpringScurity包下 SavedRequestAwareWrapper的类完全相同，只因为 SpringScurity的该
 * 类是私有类，无法供自定义的 HttpSessionRequestCache类调用
 */
public class SavedRequestAwareWrapper extends HttpServletRequestWrapper {

    //AccessDecisionManager
    public SavedRequestAwareWrapper(SavedRequest savedRequest,HttpServletRequest request) {
        super(request);
    }
}
