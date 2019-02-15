package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 通配符 demo
 *
 * java 泛型是强类型检测的，泛型类型的子类型互不相关。
 * 而我们又希望能够像使用普通类那样使泛型类型也具有面向对象的一些特性，如：向上转型为一个泛型对象，向下转型为一个泛型对象。
 * 为了使得泛型类型具有面向对象的继承关系，所以就引入了通配符的概念。
 *
 *
 * List<?> list 表示 list 是持有某种特定类型的 List，但是不知道具体是哪种类型。那么我们可以向其中添加对象吗？当然不可以，因为并不知道实际
 * 是哪种类型，所以不能添加任何类型，这是不安全的。而单独的 List list ，也就是没有传入泛型参数，表示这个 list 持有的元素的类型是 Object，
 * 因此可以添加任何类型的对象，只不过编译器会有警告信息。
 *
 * 通配符的使用可以对泛型参数做出某些限制，使代码更安全，对于上边界和下边界限定的通配符总结如下：
 * 使用 List<? extends C> list 这种形式，表示 list 可以引用一个 ArrayList ( 或者其它 List 的 子类 ) 的对象，这个 ？ 对象包含的元素类型是 C 的子类型 ( 包含 C 本身）的一种。
 * 使用 List<? super C> list 这种形式，表示 list 可以引用一个 ArrayList ( 或者其它 List 的 子类 ) 的对象，这个 ？ 对象包含的元素就类型是 C 的超类型 ( 包含 C 本身 ) 的一种。
 * 大多数情况下泛型的使用比较简单，但是如果自己编写支持泛型的代码需要对泛型有深入的了解。这几篇文章介绍了泛型的基本用法、类型擦除、泛型数组以及通配符的使用，涵盖了最常用的要点，泛型的总结就写到这里。
 */

public class TandE<T> {

    private T ob;

    public TandE(T ob){
        this.ob = ob;
    }

    //上边界通配符： <? extends Number> ，又称子类型边界
    public static void getData(TandE<? extends Number> num){
        System.out.println("class type:"+num.getClass());
    }

    //无边界通配符：<？> ，也就是没有任何限定，不做任何限制。

    //下边界通配符： <? super Apple> ，即该类型是 apple 的父类型，又称超类型边界
    public static void superType(List<? super Apple> apples){

        //add 方法执行之后，会向上造型，所以可以添加
        apples.add(new Apple());
        apples.add(new Pear());
        apples.add(new PearSon());

        //已超过范围，因为我们设置的最高级别就是 fruit
        //apples.add(new Fruit());
        System.out.println(apples);

    }

    /**
     * src 是原始数据的 List，因为要从这里面读取数据，所以用了上边界限定通配符：<? extends T>，取出的元素转型为 T。dest 是要写入的目标
     * List，所以用了下边界限定通配符：<? super T>，可以写入的元素类型是 T 及其子类型。
     */
    public static <T> void showList(List<? super T> dest,List<? extends T> src){
        for(int i=0;i<src.size();i++){
            dest.set(i,src.get(i));
        }
    }

    /**
     * 第一个 <T> 是个修饰符的功能，表示是个泛型方法，就像有static修饰的方法是个静态方法一样。它不是返回值，表示传入参数有泛型
     * 第二个 <T> 表示返回值是list类型，而这个list有泛型，只能保存 t 类型的数据
     */
    public static <T> List<T> test(T t){return null;}

    public static void main(String[] args) {

        TandE<Number> tnum = new TandE<Number>(100);
        TandE<Integer> tint = new TandE<Integer>(200);
        getData(tnum);

        //因为Integer是Number的子类
        getData(tint);

        List<? super Apple> apples = new ArrayList<Fruit>();
        superType(apples);

    }
}
