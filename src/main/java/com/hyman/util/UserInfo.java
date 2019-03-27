package com.hyman.util;

import com.hyman.entity.User;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 定义用户的所有权限相关的信息
 * Credentials ：认证信息，证书。    Container ：容器。
 *
 * CredentialsContainer ：它是一个接口，表示其实现对象包含权限等敏感数据，可以使用 eraseCredentials方法删除。在其实现类的内部对象上
 * 调用该方法。
 */
public class UserInfo extends User implements UserDetails,CredentialsContainer{

    // true 代表没有过期，false 代表已过期
    // 返回给用户的权限
    private final ArrayList<GrantedAuthority> authorities;
    // 用户帐户是否已过期
    private final boolean accountNonExpired;
    // 用户是否锁定或解锁
    private final boolean accountNonLocked;
    // 用户的认证凭据(密码)是否已过期
    private final boolean credentialsNonExpired;
    // 指示用户是否启用或禁用
    private final boolean enabled;

    public UserInfo(ArrayList<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked,
                    boolean credentialsNonExpired, boolean enabled) {
        super();
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public UserInfo(Integer id, String name, String password,ArrayList<GrantedAuthority> authorities,
                    boolean accountNonExpired, boolean accountNonLocked,
                    boolean credentialsNonExpired, boolean enabled) {
        super(id, name, password);
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    // 移除权限
    @Override
    public void eraseCredentials() {
        setPassword(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
