package demo;

import java.util.Collection;

public class Holder<T> {

    private T value;

    public Holder(){}

    public Holder(T val){
         this.value = val;
    }

    public void set(T val){
        this.value = val;
    }

    public T get(){
        return value;
    }

    @Override
    public boolean equals(Object object){
        return value.equals(object);
    }

    public static void main(String[] args) {

        Holder<Apple> appleHolder = new Holder<Apple>(new Apple());

        Apple d = appleHolder.get();
        appleHolder.set(d);

        //Holder<Fruit> fruitHolder = appleHolder;
        Holder<? extends Fruit> fruitholder = appleHolder;

        Fruit p = fruitholder.get();
        d = (Apple) fruitholder.get();

        //ClassCastException：类型转换异常
        try {
            Banana b = (Banana) fruitholder.get();
        }catch (Exception e){
            System.out.println("check:");
            System.out.println(e);
        }

        //fruitholder.set(new Apple());
        System.out.println(fruitholder.equals(d));

    }
}


class test<T>{

    public <T> void test2(T[] arr, Collection<T> collection){}

    public void test1(T[] arr, Collection<T> collection){
        for (T t:arr){
            collection.add(t);
        }
    }

    /**
     * 第一个方法相当于自动声明了一个类型为 T 的类，表示传入的参数有泛型。该方法放到任意一个类中都不会报错的。它是一个修饰符，是一个泛型方法。
     * 第二个方法相当于没有声明类型为 T 的类，这时必须在类上面加上泛型T，不然这个方法会报错的。
     *
     * 泛型的声明，必须在方法的修饰符（public,static,final,abstract等）之后，返回值声明之前。
     * 并且它和泛型类一样，可以声明多个泛型，用逗号隔开。
     */
}