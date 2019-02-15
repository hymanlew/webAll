package com.hyman.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 访问决策器（授权器），决定某个用户具有的角色，是否有足够的权限去访问某个资源。即拿到用户所拥有的权限，与权限配置一一进行比较，来判断是否能够访问
 */
public class MyAccessDecisionManager implements AccessDecisionManager{

    /**
     * Authentication ：
     *  表示当请求被 AuthenticationManager.authenticate(authentication)方法处理后，生成的拥有身份验证请求的令牌或认证的主体，即
     *  当前对象所对应的权限的集合。
     *
     * In this method, need to compare（比较） authentication with configAttributes.
     *  1, A object is a URL, a filter was find permission configuration（查找权限配置） by this URL, and pass to here.
     *  2, Check authentication has attribute in permission configuration (configAttributes)
     *  3, If not match corresponding（一致的，对应的） authentication, throw a AccessDeniedException.
     *
     *  检查用户是否够权限访问资源，
     *  参数 authentication 是从 spring的全局缓存 SecurityContextHolder中拿到的，里面是用户的权限信息
     *  参数 object 是 url
     *  参数 configAttributes 代表所需的权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        // 如果权限配置为空，则直接返回（即表示已经通过认证）
        if(configAttributes == null){
            return ;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()){
            ConfigAttribute conf = iterator.next();

            /**
             * class SecurityConfig implements ConfigAttribute
             * 系统中已设定好的当前访问所需要的权限。
             */
            String role = ((SecurityConfig)conf).getAttribute();

            /**
             * GrantedAuthority ：表示授予的身份验证对象的权限实体，它是一个接口。
             *
             * 遍历已验证主体的权限集合 authentication（即当前用户所拥有的权限集合）。 authority ：权力
             */
            for(GrantedAuthority ga :authentication.getAuthorities()){
                if(role.equals(ga.getAuthority())){
                    // 直接返回（即表示已经通过认证）
                    return;
                }
            }
        }
        // 抛出无权访问异常，注意执行到这里，后台是会抛异常的，但是界面会跳转到所配的 access-denied-page页面
        throw new AccessDeniedException("您的权限不够");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        // 指示该 AccessDecisionManager是否能够处理通过传递的ConfigAttribute提交的授权请求。
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 指示 AccessDecisionManager实现是否能够为指定的安全对象类型提供访问控制决策。
        return true;
    }
}
