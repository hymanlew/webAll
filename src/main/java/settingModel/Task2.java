package settingModel;

/**
 * 原子性的特别总结为2点：
 * 1. 对一个volatile变量的写操作，只有所有步骤完成，才能被其它线程读取到。
 * 2. 多个线程对volatile变量的写操作本质上是有先后顺序的。也就是说并发写没有问题。
 *
 * 但是 i++本身不是原子操作，是读并写。并且.volatile 并不会有锁的特性
 *
 * volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存。所以对其他线程是可见的。但需要注意一个问题，volatile只能让被他修饰内容具有可见性，但不能保证它具有原子性。
 * 比如 volatile int a = 0；之后有一个操作 a++；这个变量a具有可见性，但是a++ 依然是一个非原子操作，也就是这个操作同样存在线程安全问题。
 *
 * 比如 a=0；（a非long和double类型） 这个操作是不可分割的，那么我们说这个操作时原子操作。而 a++ 这个操作实际是a = a + 1；是可分割的，所以他不是一个原子操作。
 * 非原子操作都会存在线程安全问题，需要我们使用同步技术（sychronized）来让它变成一个原子操作。一个操作是原子操作，那么我们称它具有原子性。
 *
 * java的concurrent包下提供了一些原子类，我们可以通过阅读API来了解这些原子类的用法。比如：AtomicInteger、AtomicLong、AtomicReference等。
 *
 *
 * 在 Java 中 volatile、synchronized 和 final 实现可见性。
 * 在 Java 中 synchronized 和在 lock、unlock 中操作保证原子性。
 *
 */
public class Task2 implements Runnable{

    volatile int i = 0;

    public void run() {
        for(int j=0;j<10000;j++){
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Task2 task2 = new Task2();
        Thread t1 = new Thread(task2);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        //等待该 t1 或 t2线程执行完毕，再执行本线程
        t1.join();
        t2.join();
        System.out.println(task2.i);
    }
}


/**
 * sleep执行后线程进入阻塞状态
 * yield执行后线程进入就绪状态
 * join执行后线程进入阻塞状态
 */
