package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体事项查看网页，java 泛型总结，https://segmentfault.com/a/1190000005337789
 *
 * 当涉及到泛型时， 尽管 Apple 是 Fruit 的子类型，但是 ArrayList<Apple> 不是 ArrayList<Fruit> 的子类型，泛型不支持协变。

 */
public class TandEtest {

    public static void test1(){

        List<? extends Fruit> flist = new ArrayList<Apple>();

        //因为 filst 没有指定具体的类型，所以不能安全的添加一个对象
        //flist.add(new apple());
        //flist.add(new Fruit());

        //但是赋值为 null 可以
        flist.add(null);
        Fruit Fruit = flist.get(0);
        System.out.println(Fruit);
    }

    public static void test2(){

        //数组的协变
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();
        fruits[1] = new Pear();

        // ArrayStoreException：数组内数据类型异常
        try {
            fruits[0] = new Fruit();
        }catch (Exception e){
            System.out.println("1:");
            System.out.println(e);
        }

        try {
            fruits[1] = new Apple();
            System.out.println("YES");
        }catch (Exception e){
            System.out.println("2:");
            System.out.println(e);
        }

        // ArrayStoreException：数组内数据类型异常
        try {
            fruits[1] = new Banana();
        }catch (Exception e){
            System.out.println("3:");
            System.out.println(e);
        }

    }

    public static void main(String[] args) {

        //上边界通配符
        //test1();

        //数组的协变
        test2();
    }
}


class Fruit{}
//苹果
class Apple extends Fruit{};
//梨
class Pear extends Apple{};
//other
class PearSon extends Pear{}
//香蕉
class Banana extends Fruit{};