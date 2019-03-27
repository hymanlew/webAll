package settingModel5;

import java.util.Arrays;

/**
 * 模版方法模式（Template method pattern）：定义一个操作中算法的框架，而将一些步骤延迟到子类中，使得子类可以不改变算法的结构即可重定义该算
 *          法中的某些特定步骤。是一种行为类模式。
 *
 * 事实上模版方法是编程中一个经常用到的模式。例如：某日，程序员A拿到一个任务：给定一个整数数组，把数组中的数由小到大排序，然后把排序之后的结果
 * 打印出来。经过分析之后，这个任务大体上可分为两部分，排序和打印，打印功能好实现，排序就有点麻烦了。但是A有办法，先把打印功能完成，排序功能另找人做。
 */
public abstract class AbstractSort {
    /**
     * 将数组array由小到大排序
     * @param array
     */
    protected abstract void sort(int[] array);

    public void showSortResult(int[] array){
        this.sort(array);
        System.out.println("排序结果：");
        for(int i=0;i<array.length;i++){
            // 横向排列，需要以空格来隔开
            System.out.printf("%3s", array[i]);
        }
    }
}

/**
 * 写完后，A找到刚毕业入职不久的同事B说：有个任务，主要逻辑我已经写好了，你把剩下的逻辑实现一下吧。于是把AbstractSort类给B，让B写实现。
 * B拿过来一看，太简单了，10分钟搞定，代码如下：
 */
 class ConcreteSort extends AbstractSort{

     @Override
    protected void sort(int[] array) {
        for(int i=0;i<array.length;i++){
            dosort(array,i);
        }
    }

    private void dosort(int[] array,int index){

         int minvalue = 31767;  // 最小值变量
         int indexmin = 0;      // 最小值索引变量
         int temp;              // 暂存变量

        //找到最小值并保存
         for(int i=index;i<array.length;i++){
             if(array[i]<minvalue){
                 minvalue = array[i];
                 indexmin = i;
             }
         }

         // 交换位置，完成排序
         temp = array[index];
         array[index] = array[indexmin];
         array[indexmin] = temp;
    }
}

// Client
class Client{

     public static int[] a = {10,32,1,9,5,7,12,0,4,3};

     public static void sort(int[] array){

         for(int i =0;i<array.length;i++){
             for(int k=0;k<array.length-i-1;k++){
                if(array[k]>array[k+1]){
                    int small = array[k+1];
                    array[k+1] = array[k];
                    array[k] = small;
                }
             }
         }
         System.out.println(Arrays.toString(a));
     }

    public static void main(String[] args) {

        //sort(a);
        AbstractSort s = new ConcreteSort();
        s.showSortResult(a);
    }
}
/**
 *  运行正常。行了，任务完成。没错，这就是模版方法模式。大部分刚步入职场的毕业生应该都有类似B的经历。一个复杂的任务，由公司中的牛人们
 *  将主要的逻辑写好，然后把那些看上去比较简单的方法写成抽象的，交给其他的同事去开发。这种分工方式在编程人员水平层次比较明显的公司中经
 *  常用到。比如一个项目组，有架构师，高级工程师，初级工程师，则一般由架构师使用大量的接口、抽象类将整个系统的逻辑串起来，实现的编码则
 *  根据难度的不同分别交给高级工程师和初级工程师来完成。怎么样，是不是用到过模版方法模式？
 */
