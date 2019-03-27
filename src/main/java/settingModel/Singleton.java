package settingModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//饿汉式单例：
public class Singleton {

    //private static Singleton singleton = new Singleton();
    //
    //private Singleton(){}
    //
    //public static Singleton getSingleton(){
    //    return singleton;
    //}
    private static final Singleton instance2 = new Singleton();

    private Singleton(){
        //do something
    }

    public static Singleton getInstance(){
        if(instance2==null){
            synchronized (Singleton.class){
                if(instance2==null){
                    //instance = new SingleDoubleLock();
                    return new Singleton();
                }
            }
        }
        return instance2;
    }
}

/**
 * 懒汉式单例：
 *
 * 这个单例模式看起来很完美，如果 singleLock为空，则加锁，只有一个线程进入同步方法中完成对象的初始化，然后instance不为空，那么后续的所有线
 * 程获取instance都不用加锁，从而提升了性能。
 *
 * 但是此时对象的赋值操作可能会存在重排序，即当前线程初始化对象时（new SingleLock）执行到一半，其它线程如果进来执行到 null判断，而此时 singleLock
 * 已经不为null，因此将会读取到一个没有初始化完成的对象。而此过程就是重排序，造成顺序的颠倒。
 */
class SingleLock{

    private static SingleLock singleLock;

    private SingleLock(){}

    public static synchronized SingleLock getSingletonl(){

        if(singleLock==null){
            singleLock = new SingleLock();
        }
        return  singleLock;
    }
}

/**
 * 但如果将instance用volatile来修饰，就完全不一样了，对 instance的写入操作将会变成一个原子操作，没有初始化完，就不会被刷新到主存中。
 *
 * 当一个变量定义为 volatile 之后，将具备两种特性：
 　　1.保证此变量对所有的线程的可见性，即当一个线程修改了这个变量的值，volatile 保证了新值能立即同步到主内存，
     以及每次使用前立即从主内存刷新。但普通变量做不到这点，普通变量的值在线程间传递均需要通过主内存（详见：Java内存模型）来完成。

 　　2.禁止指令重排序优化。有volatile修饰的变量，赋值后多执行了一个“load addl $0x0, (%esp)”操作，这个操作相当于一个内存屏障（指令重排序时
     不能把后面的指令重排序到内存屏障之前的位置），只有一个CPU访问内存时，并不需要内存屏障；

     指令重排序：是指CPU采用了允许将多条指令不按程序规定的顺序分开发送给各相应电路单元处理。

 * 原子性的特别总结为2点：
    1. 对一个volatile变量的写操作，只有所有步骤完成，才能被其它线程读取到。
    2. 多个线程对volatile变量的写操作本质上是有先后顺序的。也就是说并发写没有问题。

 * 但是 i++本身不是原子操作，是读并写。并且 volatile 并不会有锁的特性
 */

/**
 *  这种模式中双重判断加同步的方式，比第一个例子中的效率大大提升，因为如果单层if判断，在服务器允许的情况下，假设有一百个线程，耗费的时间为100*
 *  （同步判断时间+if判断时间），而如果双重if判断，100的线程可以同时if判断，理论消耗的时间只有一个if判断的时间。
 *  所以如果面对高并发的情况，而且采用的是懒汉模式，最好的选择就是双重判断加同步锁的方式。
 */
class SingleDoubleLock{

    //private static volatile SingleDoubleLock instance = null;
    private static final SingleDoubleLock instance2 = new SingleDoubleLock();

    private SingleDoubleLock(){
        //do something
    }

    public static SingleDoubleLock getInstance(){
        if(instance2==null){
            synchronized (SingleDoubleLock.class){
                if(instance2==null){
                    //instance = new SingleDoubleLock();
                    return new SingleDoubleLock();
                }
            }
        }
        return instance2;
    }

}

/**
 * 在一个jvm中会出现多个单例吗：
 *   在分布式系统、多个类加载器、以及序列化的的情况下，会产生多个单例，这一点是无庸置疑的。那么在同一个jvm中，会不会产生单例呢？使用单例提供的
 *   getInstance()方法只能得到同一个单例，除非是使用反射方式，才会得到新的单例。
 */
class SingleTest{

    /**
     * 而下面的代码，在每次运行都会产生新的单例对象。所以运用单例模式时，一定注意不要使用反射产生新的单例对象。
     */
    public void test() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class c = Class.forName(Singleton.class.getName());
        //得到当前类的公共构造器
        Constructor constructor = c.getDeclaredConstructor();
        //设置是否能够访问类中所有的属性，包括 final，private,static
        constructor.setAccessible(true);
        Singleton singleton = (Singleton) constructor.newInstance();
    }

    public static void main(String[] args) {
        SingleDoubleLock.getInstance();
    }
    /**
     * Constructor 提供某个类的单个构造方法的信息以及对它的访问权限。
     * 并允许在将实参与带有底层构造方法的形参的 newInstance() 匹配时进行扩展转换。
     *
     * 个人觉得只要Field.setAccessible(true)之后，即使是final关键字标示过得属性也可以有访问权限！这样的反射会改变JAVA的结构，甚至你的代
     * 码可维护性，你完全可以改别的代码里面的值 ，所以这只是一个简单的例子。只是验证通过反射能做一些让你无法想象的东西。。
     */
}