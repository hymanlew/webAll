package com.hyman.security;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Invocation ：调用；
 *
 * FilterInvocationSecurityMetadataSource ：该接口被设计用于执行对 FilterInvocations（用于验证的请求，响应对象）的查找。
 * 并且为指定的安全对象类型提供 ConfigAttributes（权限属性）。
 *
 * FilterInvocation 类，安全对象：
 * 它持有与 HTTP过滤器关联的对象。保证请求和响应是 HttpServletRequest 和 HttpServletResponse的实例，并且没有空对象。
 * 它要求安全系统类可以获得对筛选器环境的访问，以及请求和响应。
 * 这个类的作用本身很简单，就是把 doFilter传进来的 request,response 和 FilterChain对象保存起来，供FilterSecurityInterceptor的
 * 处理代码调用。
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    @Resource
    private transient SqlSessionTemplate sqlSessionTemplate;

    // private RequestMatcher requestMatcher = new AntPathRequestMatcher();

    // resourceMap 是所有权限与之相应资源的键值对
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;


    /**
     * 在 tomcat服务器启动的时候，读取所有的权限和相应的路径资源，只实例化一次
     *
     * 这种缓存的实现其实有一个缺点，因为 loadResourceDefine 方法是放在构造器上调用的，而这个类的实例化只在 web服务器启动
     * 时调用一次，那就是说 loadResourceDefine方法只会调用一次，如果资源和权限的对应关系在启动后发生了改变，那么缓存起来的
     * 就是脏数据，而这里使用的就是缓存数据，那就会授权错误了。但如果资源和权限对应关系是不会改变的，这种方法性能会好很多。
     */
    public MyInvocationSecurityMetadataSource(){
        loadResourceDefine();
    }

    public MyInvocationSecurityMetadataSource(
            SqlSessionTemplate sqlSessionTemplate) {
        super();
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    /**
     * 该批注允许您选择性地取消特定代码段（即，类或方法）中的警告。其中的想法是当您看到警告时，您将调查它，如果您确定它不是问题，虽然它
     * 听起来似乎会屏蔽潜在的错误，但实际上它将提高代码安全性，因为它将防止您对警告无动于衷 — 您看到的每一个警告都将值得注意。
     */

    //tomcat开启时加载一次，加载所有 url 和 权限（或角色）的对应关系
    @SuppressWarnings("unchecked")
    private void loadResourceDefine() {

        //权限资源的访问地址
        List<String> urlList = null;
        resourceMap=new HashMap<String, Collection<ConfigAttribute>>();

        // ConfigAttribute ：存储与安全系统相关的配置属性，主要是权限属性。
        //Collection<ConfigAttribute> attr=new ArrayList<ConfigAttribute>();

        //List<String>list=sqlSessionTemplate.selectList("tbl_resource.getAuthoritys");
        List<String> list = new ArrayList<>();
        list.add("USER");
        list.add("ADMIN");

        // 遍历每个权限 authority，并存储其对应的 url
        for(String authority:list){
            ConfigAttribute ca = new SecurityConfig(authority);

            /**
             * urlList = sqlSessionTemplate.selectList("tbl_resource.getUrl",authority);
             *
             * 之所以使用 sqlSessionTemplate，是因为 spring的机制（依赖注入），dao需要依赖注入吧，但这是在启动时候，那个
             * dao可能都还没加载，所以这里需要读者自己写sessionFactory，自己写hql或sql（这里直接调用 mybatis类）。
             *
             * 或者也可以直接在 getAttributes 方法里面调用dao（这个是加载完，后来才会调用的，所以可以使用dao），通过被拦
             * 截 url获取数据库中的所有权限，封装成 Collection<ConfigAttribute> 返回就行了。（灵活、简单）
             */
            urlList = new ArrayList<>();
            urlList.add("/");

            for(String url:urlList){
                if(resourceMap.containsKey(url)){
                    Collection<ConfigAttribute> attrs=resourceMap.get(url);
                    attrs.add(ca);
                    resourceMap.put(url, attrs);
                }else {
                    Collection<ConfigAttribute> attrs =new ArrayList<ConfigAttribute>();
                    attrs.add(ca);
                    resourceMap.put(url, attrs);
                }
            }
        }
    }

    // 在登录后访问时进行当前用户与其所对应的权限的匹配。参数是，要访问的url，返回这个 url 对应的所有权限（或角色）
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        // 抽取出待请求的URL ，获得请求路径
        String url = ((FilterInvocation)object).getRequestUrl();
        HttpServletRequest requestUrl = ( (FilterInvocation)object ).getHttpRequest();

        // 得到所有路径和权限集合的键值对，并遍历它们
        Iterator<String> iterator = resourceMap.keySet().iterator();
        while (iterator.hasNext()){
            String resurl = iterator.next();

            /**
             * RequestMatcher：是一个接口，是匹配 HttpServletRequest的简单策略。
             * 这一句是创建路径比对器
             */
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resurl);

            // 匹配带有指定路径的比较器对象，与前台发送的请求对象，其内部会分析两者的 url路径
            // 在本项目中所有的用户都可以根据自己的权限进行系统中，即根目录下
            if( requestMatcher.matches(requestUrl) ){
                //如果访问的路径与map中的路径相同，返回相应的权限集合
                return resourceMap.get(resurl);
            }
        }
        //如果始终都没有匹配的路径，返回null
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        /**
         * 该方法指示 SecurityMetadataSource（是一个接口）的实现是否能够为指定的安全对象类型提供 ConfigAttributes（权限属性）。
         * FilterInvocationSecurityMetadataSource 就是它的子接口。
         *
         * SecurityMetadataSource 接口，是由存储的类实现，并可以识别应用于给定安全对象调用的 ConfigAttributes（权限属性）。
         */
        return true;
    }
}
