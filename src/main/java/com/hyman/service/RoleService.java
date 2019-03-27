package com.hyman.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 做为
 * @author hyman
 */
@Component
public class RoleService {
    /**
     * 该 service用于获取 user用户的对应的权限
     */
    public List<String> getRoleId(){
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        return ids;
    }
}
