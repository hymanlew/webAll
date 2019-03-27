package settingModel8;

import java.util.*;

// 复合享元模式：就是将一些单纯享元使用合成模式加以复合，形成复合享元对象。这样的复合享元对象本身不能共享，但是它们可以分解成单纯享元对象，
// 而后者则可以共享。
// 抽象享元角色类
public interface Flyweight2 {
    //一个示意性方法，参数state是外蕴状态
    public void operation(String state);
}

// 具体享元角色类
class ConcreteFlyweight2 implements Flyweight2{
    // Character 类在对象中包装一个基本类型 char 的值。Character 类型的对象包含类型为 char 的单个字段。
    private Character intrinsicState = null;

    // 构造函数，内蕴状态作为参数传入
    public ConcreteFlyweight2(Character state){
        this.intrinsicState = state;
    }

    // 外蕴状态作为参数传入方法中，改变方法的行为，但是并不改变对象的内蕴状态。
    public void operation(String state) {
        // 内蕴状态值
        System.out.println("Intrinsic State = " + this.intrinsicState);
        // 外蕴状态值
        System.out.println("Extrinsic State = " + state);
    }
}

/**
 * 复合享元对象是由单纯享元对象通过复合而成的，因此它提供了add()这样的聚集管理方法。由于一个复合享元对象具有不同的聚集元素，这些聚集元素在
 * 复合享元对象被创建之后加入，这本身就意味着复合享元对象的状态是会改变的，因此复合享元对象是不能共享的。
 *
 * 复合享元角色实现了抽象享元角色所规定的接口，也就是operation()方法，这个方法有一个参数，代表复合享元对象的外蕴状态。一个复合享元对象的所
 * 有单纯享元对象元素的外蕴状态都是与复合享元对象的外蕴状态相等的；而一个复合享元对象所含有的单纯享元对象的内蕴状态一般是不相等的，不然就没有
 * 使用价值了。
 */
class ConcreteCompositeFlyweight2 implements Flyweight2{
    private Map<Character,Flyweight2> files = new HashMap<Character, Flyweight2>();

    // 增加一个新的单纯享元对象到聚集中
    public void add(Character key,Flyweight2 fly){
        files.put(key,fly);
    }

    // 外蕴状态作为参数传入到方法中
    public void operation(String state) {
        Flyweight2 fly = null;
        for(Object o : files.keySet()){
            fly = files.get(o);
            fly.operation(state);
        }
    }
}

class FlyweightFactory2{
    private Map<Character,Flyweight2> files = new HashMap<Character, Flyweight2>();

    // 复合享元工厂方法
    public Flyweight2 factory(List<Character> compositeState){
        ConcreteCompositeFlyweight2 compositeFly = new ConcreteCompositeFlyweight2();
        for(Character state:compositeState){
            compositeFly.add(state,new ConcreteFlyweight2(state));
        }
        return compositeFly;
    }

    // 单纯享元工厂方法
    public Flyweight2 factory(Character state){
        // 先从缓存中查找对象
        Flyweight2 fly = files.get(state);
        if(fly==null){
            //如果对象不存在则创建一个新的Flyweight对象
            fly = new ConcreteFlyweight2(state);
            //把这个新的Flyweight对象添加到缓存中
            files.put(state, fly);
        }
        return fly;
    }
}

class Client4{
    public static void main(String[] args) {

        List<Character> compositeState = new ArrayList<Character>();
        compositeState.add('a');
        compositeState.add('b');
        compositeState.add('c');
        compositeState.add('a');
        compositeState.add('b');

        FlyweightFactory2 factory = new FlyweightFactory2();
        // chart 类型数据是使用单引号的
        Flyweight2 fly1 = factory.factory(compositeState);
        Flyweight2 fly2 = factory.factory(compositeState);
        fly1.operation("Composite Call");

        System.out.println("---------------------------------");
        System.out.println("复合享元模式是否可以共享对象：" + (fly1 == fly2));

        Character state = 'a';
        Flyweight2 fly3 = factory.factory(state);
        Flyweight2 fly4 = factory.factory(state);
        System.out.println("单纯享元模式是否可以共享对象：" + (fly3 == fly4));
    }

    /**
     * 从运行结果可以看出：
     *
     * 一个复合享元对象的所有单纯享元对象元素的外蕴状态都是与复合享元对象的外蕴状态相等的。即外运状态都等于Composite Call。
     *
     * 一个复合享元对象所含有的单纯享元对象的内蕴状态一般是不相等的。即内蕴状态分别为b、c、a。
     *
     * 复合享元对象是不能共享的。即使用相同的对象compositeState通过工厂分别两次创建出的对象不是同一个对象。
     *
     * 单纯享元对象是可以共享的。即使用相同的对象state通过工厂分别两次创建出的对象是同一个对象。
     */
}