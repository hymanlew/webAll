package com.hyman.securityDemo;

/**
 * 在war里注册 springSecurityFilterChain. 这可以通过Spring在Servlet 3.0+环境中对WebApplicationInitializer的支持进行Java配置
 */


//public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
//
//    /**
//     * 这段代码仅仅会将 springSecurityFilterChain 注册到应用中，并作用于每个url。此时我们的SecurityConfig类依然会被已经存在的
//     * ApplicationInitializer 加载。例如，我们使用SpringMvc，它将会被添加到 getRootConfigClasses()。
//     * @return
//     */
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[] { SecurityConfig.class };
//    }
//
//    // ... other overrides ...
//}
