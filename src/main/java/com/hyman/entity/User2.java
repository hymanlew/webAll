package com.hyman.entity;

import javax.annotation.Generated;
import java.time.LocalDateTime;

public class User2 {

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime gmtCreate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime gmtModified;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String hobby;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRealName() {
        return realName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getHobby() {
        return hobby;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }
}