package com.hyman.securityDemo;

public class Detail {
    /**
     * 本包下的 demo类文件，都是简化并重写的源码，不需要 xml配置文件和 web.xml filter的配置，因为它们会互相冲突。
     *
     * 总结：
     * FormLoginConfigurer 是 AbstractAuthenticationFilterConfigurer的子类。
     * UsernamePasswordAuthenticationFilter 是 AbstractAuthenticationProcessingFilter的子类。
     * FormLoginConfigurer 用于创建 UsernamePasswordAuthenticationFilter
     *
     * 并且在使用源码 jar包时，在本项目中必须不能有继承源码的类（除非是已经重写好的，功能全部OK），因为即使没有指定扫描该类所在的包，
     * 但项目在运行并调用 jar包时，还是会进入到其子类。
     * 所以为了让项目正常运行，故把所有的类全部注释掉。
     */

    /*
    手动添加被认证的用户到 security session中（通常是在 interceptor 中对特定的不需要认证的用户进行管理，然后手动认证并添加）：
     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
     SecurityContextHolder.getContext().setAuthentication(authentication);

    通常当用户登录，经过 security 认证时，该用户会被放到 SecurityContext中。但是在完成认证后（即过滤链执行完时），org.spring
    framework.security.web.context.SecurityContextPersistenceFilter 类会调用
    SecurityContextHolder.clearContext() 把 SecurityContextHolder清空，但是在正常访问时
    SecurityContextHolder.getContext().getAuthentication() .getPrincipal() 还是可以获取到被认证后的用户。

    经过 spring security认证后，它会把一个 SecurityContextImpl对象存储到session中，此对象中有当前用户的各种
    资料，所以只会认证一次。
    SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute
    ("SPRING_SECURITY_CONTEXT");
    securityContextImpl.getAuthentication().getName());


    security 框架在经过 UsernamePasswordAuthenticationFilter 的 attemptAuthentication 方法（对页面的 username，password进
    行绑定，然后内部调用（AuthenticationManager，ProviderManager等等），但最后都是调用 userdetailService 连接数据库对用户进
    行认证，并绑定角色权限），如果认证成功会调用 successfulAuthentication，该方法不仅调用了successHandler，还执行了
    SecurityContextHolder.getContext().setAuthentication(authResult);

    SecurityContextHolder 是对于 ThreadLocal的封装，它放入了authResult，再其他地方也可以取出来的。最后就是
    在SecurityContextPersistenceFilter中取出了authResult，并存入了session，同时清空 context上下文。期间还有
    requestcache缓存，记住用户等等的缓存。


    security 在项目启动时要事先加载所有角色对应的权限，当然这跟 security 框架无关，只是后者在后面需要用
    到它。或者也可以在 FilterInvocationSecurityMetadataSource 中访问数据库获取所有的资源进行定义，它储存的
    是请求与权限的对应关系。

    1，getAllConfigAttributes()，spring 容器启动时自动调用的, 返回所有权限的集合. 一般把所有请求与权限的对应
    关系也要在这个方法里初始化, 保存在一个属性变量里。

    2，getAttributes(Object object)，每当接收到一个http请求时, 该类都会调用的方法. 参数object是一个包含url信息
    的HttpServletRequest 实例。这个方法要返回请求该 url所需要的所有权限集合。

    AccessDecisionManager 访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源（即授权）
    。授权方法 decide。对一次访问授权，需要传入三个信息。

    1，认证过的票据Authentication，确定了谁正在访问资源。
    2，被访问的资源 object。
    3，访问资源要求的权限配置 ConfigAttributeDefinition。

    从 Authentication 中可以获取认证用户所拥有的权限（角色权限），再对比访问资源要求的权限，即可断定当
    前认证用户是否能够访问该资源。
    如果有就不会再执行 decide 授权方法。如果没有权限（通常是没登录时）才会走授权方法进行验证。

    先 FORM_LOGIN_FILTER（登录认证），后 FILTER_SECURITY_INTERCEPTOR（资源认证，缓存等等）。


     */
}
