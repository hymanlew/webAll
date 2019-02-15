package settingModel;

/**
 有序性：
 　　Java 语言提供了 volatile 和 synchronized 两个关键字来保证线程之间操作的有序性：
    volatile 是因为其本身包含“禁止指令重排序”的语义。
    synchronized 是由“一个变量在同一个时刻只允许一条线程对其进行 lock 操作”这条规则获得的，此规则决定了持有同一个对象锁的两个同步块只能串行执行。


 Volatile原理：
 　　Java语言提供了一种稍弱的同步机制，即volatile变量，用来确保将变量的更新操作通知到其他线程。当把变量声明为volatile类型后，编译器与运行时都会注
    意到这个变量是共享的，因此不会将该变量上的操作与其他内存操作一起重排序。
    volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，因此在读取volatile类型的变量时总会返回最新写入的值。

 　　在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比sychronized关键字更轻量级的同步机制。

 　　当对非 volatile 变量进行读写的时候，每个线程先从内存拷贝变量到CPU缓存中。如果计算机有多个CPU，每个线程可能在不同的CPU上被处理，这意味着每个线程可以
    拷贝到不同的 CPU cache 中。

 　　而声明变量是 volatile 的，则JVM 保证了每次读变量都从内存中读，跳过 CPU cache 这一步。

 volatile 性能：
 　　volatile 的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行。

 */
public class LineTest {

     private static boolean ready;
     private static int number;

     private static class ReaderThread extends Thread {
         @Override
         public void run() {

             while(!ready) {
                 //System.out.println(ready);
                 Thread.yield();
             }
             System.out.println(number);
         }
     }

    public static void main(String[] args) throws InterruptedException {

        new ReaderThread().start();
        System.out.println("main:"+ready);
        Thread.sleep(100);
         number = 42;
         ready = true;
        System.out.println("final:"+ready);
     }
}

/**
 * 　NoVisibility可能会持续循环下去，因为读线程可能永远都看不到ready的值。甚至NoVisibility可能会输出0，因为读线程可能看到了写入ready的值，
 *   但却没有看到之后写入number的值，这种现象被称为“重排序”。
 *   只要在某个线程中无法检测到重排序情况（即使在其他线程中可以明显地看到该线程中的重排序），那么就无法确保线程中的操作将按照程序中指定的顺序来执行。
 *   当主线程首先写入number，然后在没有同步的情况下写入ready，那么读线程看到的顺序可能与写入的顺序完全相反。
 *
 *   在没有同步的情况下，编译器、处理器以及运行时等都可能对操作的执行顺序进行一些意想不到的调整。在缺乏足够同步的多线程程序中，要想对内存操作的
 *   执行持续进行判断，无法得到正确的结论。
 *
 *   这个看上去像是一个失败的设计，但却能使JVM充分地利用现代多核处理器的强大性能。例如在缺少同步的情况下，Java内存模型允许编译器对操作顺序进行重
 *   排序，并将数值缓存在寄存器中。此外它还允许CPU对操作顺序进行重排序，并将数值缓存在处理器特定的缓存中。从而最终得出正确的结果。
 *
 *   并且其重排序的特性，与可见性的特点，两者是可以互相印证的。
 */
