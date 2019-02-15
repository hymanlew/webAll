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
}
