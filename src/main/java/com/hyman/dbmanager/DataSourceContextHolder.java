package com.hyman.dbmanager;

import org.springframework.util.StringUtils;

/**
 * 多数据源切换
 */
public class DataSourceContextHolder {

    // 用ThreadLocal来设置当前线程使用哪个dataSource
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 测试数据库
    public static final String DATA_SOURCE_TEST = "testSource";
    // 真实数据库
    public static final String DATA_SOURCE_REAL = "dataSource";


    public static void setDataSource(String sourceType){
        contextHolder.set(sourceType);
    }

    public static String getDataSource(){

        String datasource = contextHolder.get();
        if(StringUtils.isEmpty(datasource)){

            // 此处返回的默认值，必须要与 xml 中定义的默认连接是一致的，否则业务会乱
            return DATA_SOURCE_TEST;
        }else {
            return datasource;
        }
    }

    public static void clearDataSource(){
        contextHolder.remove();
    }


    /**
     * 另外值得注意的是可以在本类中使用 ThreadLocal 类的 set 方法来设置当前线程要选择的 dataSource，看一下set方法的源码：
     *
     * ThreadLocal.set()
     *
     * public void set(T value) {
            Thread t = Thread.currentThread();
            ThreadLocalMap map = getMap(t);
            if (map != null)
                map.set(this, value);
            else
                createMap(t, value);
            }

     *
     * 显而易见，获取当前线程，并且使用一个 hashmap 把需要存储的值设置进去。因为tomcat是用的线程池来处理每个请求，所以用 ThreadLocal
     * 可以保证线程安全问题。同时这个 AbstractRoutingDataSource 类也值得好好研究一下。
     */
}
