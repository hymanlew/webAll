package settingModel;

/**
 * volatile 的可见性，是指线程访问变量是否是最新值。是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。
 */
public class Task implements Runnable{

    /**
     * 当在并发执行时，thread1 线程修改的 flag值并不会立即被刷新到主存中去，从而导致 thread线程中的 flag仍然为 true，仍会继续运行，形成
     * 死循环。这就是因为线程对变量的实际值不可见性。
     * （要特别注意不要执行 run 方法中的输出语句，因为此时的 flag 变量是非线程同步的，并且当 while 循环中不存在 flag 变量的操作时，虚拟机
     *   会认为该变量是稳定的，代码会在编译时被优化为：
     *
         if(flag){
         while(true){
         i++;
         }
         }

       而当在循环内部执行输出的操作时（但其他的操作不影响），它会自动刷新来自主存中的数据）。
     *
     * 加上 volatile 修饰符就能够实现线程对变量的可见性。
     * 可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。
     *
     * 并且同步块也存在如下语义：
        1.进入同步块，访问共享变量会去读取主存
        2.退出同步块，本地内存对共享变量的修改会立即刷新到主存
     */

    boolean flag = true;
    //volatile boolean flag = true;
    int i = 0;

    public void run() {
        while (flag){
            i++;
            //System.out.println("test");
            //i-=1;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Task task =new Task();
        Thread thread = new Thread(task);
        thread.start();

        //阻塞指定的毫秒数
        //Thread.sleep(10);
        //Thread thread1 = new Task1(task);
        //thread1.start();

        //Thread thread1 = new Task1(task){
        //    public void run(){
        //        if(task.i>100){
        //            task.flag = false;
        //        }
        //        System.out.println("test:"+task.i);
        //    }
        //};
        //thread1.start();

        Thread.sleep(100);
        task.flag = false;
        System.out.println("test:"+task.i);
        Thread.sleep(100);

        System.out.println("final:"+task.i);
        System.out.println("stop");
    }

}

class Task1 extends Thread{

    Task task = null;
    int k = 0;

    public Task1(Task task){
        this.task = task;
    }

    @Override
    public void run(){
        while (task.flag){
            k++;
            if(k>=100){
                task.flag = false;
                System.out.println("test:"+task.i);
            }
        }

    }
}