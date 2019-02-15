package settingModel4;

//子系统角色，由若干个类组成
public class SubClass1 {

    public void  method1(){
        System.out.println("这是子系统类1中的方法1");
    }
    public void method2(){
        System.out.println("这是子系统类1中的方法2");
    }
}

class SubClass2{

    public void  method1(){
        System.out.println("这是子系统类2中的方法1");
    }
    public void method2(){
        System.out.println("这是子系统类2中的方法2");
    }
}

class SubClass3{

    public void  method1(){
        System.out.println("这是子系统类3中的方法1");
    }
    public void method2(){
        System.out.println("这是子系统类3中的方法2");
    }
}

/**
 * 外观模式（Facade）：为子系统中的一组接口提供一个一致的界面，Facade模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 *
 * 外观角色类：
 */
class FacadeClass{
    public void facemethod(){
        SubClass1 s1 = new SubClass1();
        s1.method1();
        SubClass2 s2 = new SubClass2();
        s2.method1();
        SubClass3 s3 = new SubClass3();
        s3.method1();
    }
}


class ClientClass{
    public static void main(String[] args) {
        FacadeClass fc = new FacadeClass();
        fc.facemethod();
    }
}

/**
 *  Facade类其实相当于子系统中SubClass类的外观界面，有了这个Facade类，那么客户端就不需要亲自调用子系统中的那些具体实现的子类了，也不需要知
 *  道系统内部的实现细节，甚至都不需要知道这些子类的存在，客户端只需要跟Facade类交互就好了，从而更好地实现了客户端和子系统中具体类的解耦，让客
 *  户端更容易地使用系统。
 *
 *  同时，这样定义一个Facade类可以有效地屏蔽内部的细节，免得客户端去调用Module类时，发现一些不需要它知道的方法。如上代码，我的所有子类中的方
 *  法二都是方法一调用的方法，是配合方法一的，他们不需要被客户端调用，而且具有一定的保密性，这样使用外观模式时就可以不被客户端知道。
 */
