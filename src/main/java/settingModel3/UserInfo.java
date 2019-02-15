package settingModel3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式（Proxy pattern）：为其他对象提供一种代理以控制对这个对象的访问。在某些情况下，一个对象不适合或者不能直接引用另一个对象，而代理对象可以在客户端和目
 *          标对象之间起到中介的作用。
 * 静态代理，也就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。
 *
 * 抽象角色，真实对象和代理对象共同的接口：
 */
public interface UserInfo {
    //查询用户
    public void queryUser();
    //更新用户
    public void updateUser();
}


//真实角色
class UserImpl implements UserInfo{

    public void queryUser() {
        System.out.println("find user");
    }

    public void updateUser() {
        System.out.println("update user");
    }
}


//代理角色
class UserProxy implements UserInfo{

    private UserInfo userimp;

    public UserProxy(UserInfo userimp){
        this.userimp = userimp;
    }

    public void queryUser() {
        System.out.println("find user proxy");
        //这里可以扩展，增加一些查询之前需要执行的方法
        //查询方法略...
        //这里可以扩展，增加一些查询之后需要执行的方法
    }

    public void updateUser() {
        System.out.println("update user proxy");
        //这里可以扩展，增加一些修改之前需要执行的方法
        //修改方法略...
        //这里可以扩展，增加一些修改之后需要执行的方法
    }
}


class ProxyTest{
    public static void main(String[] args) {

        UserInfo userInfo = new UserImpl();
        UserInfo userproxy = new UserProxy(userInfo);
        userproxy.queryUser();
        userproxy.updateUser();
    }
}


/**
 * 动态代理类的源码是程序在运行期间由JVM根据反射等机制动态生成的，所以不存在代理类的字节码文件。代理角色和真实角色的联系在程序运行时确定。
 *
 * 抽象角色，真实角色均不用改变，代理角色处理器需要改动。
 */
class UserHandler implements InvocationHandler{

    private UserInfo userimp;

    public UserHandler(UserInfo userimp){
        this.userimp = userimp;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object object = null;
        //方法开始前做一些事情
        System.out.println("动态代理 start");
        if(method.getName().equals("queryUser")){
            /**
             *  invoke(class, method)，是动态地调用并执行类对象中的方法，即把方法参数化（执行无参方法）
             *  invoke(class, object[])，是动态地调用并执行类中的方法，并把参数数组传入（执行有参方法）
             */
            object = method.invoke(userimp,args);
            //激活调用的方法
            System.out.println("动态代理 running");
        }
        //方法结束后做一些事情
        System.out.println("动态代理 end");
        return object;
    }
}

class ProxyTest1{
    public static void main(String[] args) {

        UserInfo userInfo = new UserImpl();
        UserHandler userHandler = new UserHandler(userInfo);
        UserInfo proxyresult =
                (UserInfo) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{UserInfo.class},userHandler);
        proxyresult.queryUser();
    }
}