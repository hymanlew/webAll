package settingModel8;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式（Flyweight）：它是对象的结构模式，是以共享的方式高效地支持大量的细粒度对象。
 *
 * 在JAVA语言中，String类型就是使用了享元模式。String对象是final类型，对象一旦创建就不可改变。在JAVA中字符串常量都是存在常量池中的，
 * JAVA会确保一个字符串常量在常量池中只有一个拷贝。String a="abc"，其中"abc"就是一个字符串常量。这样的设计避免了在创建N多相同对象时
 * 所产生的不必要的大量的资源消耗。
 *
 * 享元模式的结构：
 *　 享元模式采用一个共享来避免大量拥有相同内容对象的开销。这种开销最常见、最直观的就是内存的损耗。享元对象能做到共享的关键是区分内蕴状态
 *  (Internal State) 和 外蕴状态(External State)。
 *
 *  一个内蕴状态是存储在享元对象内部的，并且是不会随环境的改变而有所不同。因此一个享元可以具有内蕴状态并可以共享。
 *  一个外蕴状态是随环境的改变而改变的、不可以共享的。享元对象的外蕴状态必须由客户端保存，并在享元对象被创建之后，在需要使用的时候再传入到
 *  享元对象内部。外蕴状态不可以影响享元对象的内蕴状态，它们是相互独立的。
 *
 * 享元模式可以分成单纯享元模式和复合享元模式两种形式：
 */

// 在单纯的享元模式中，所有的享元对象都是可以共享的。
// 抽象享元角色类
public interface Flyweight {
    //一个示意性方法，参数state是外蕴状态
    public void operation(String state);
}

/**
 * 具体享元角色类ConcreteFlyweight有一个内蕴状态，在本例中一个Character类型的intrinsicState属性代表，它的值应当在享元对象被创建时赋予。
 * 所有的内蕴状态在对象创建之后，就不会再改变了。
 *
 * 如果一个享元对象有外蕴状态的话，所有的外部状态都必须存储在客户端，在使用享元对象时，再由客户端传入享元对象。这里只有一个外蕴状态，
 * operation()方法的参数state就是由外部传入的外蕴状态。
 */
class ConcreteFlyweight implements Flyweight{

    // Character 类在对象中包装一个基本类型 char 的值。Character 类型的对象包含类型为 char 的单个字段。
    private Character intrinsicState = null;

    // 构造函数，内蕴状态作为参数传入
    public ConcreteFlyweight(Character state){
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
 * 享元工厂角色类，必须指出的是客户端不可以直接将具体享元类实例化，而必须通过一个工厂对象，利用一个factory()方法得到享元对象。一般而言，
 * 享元工厂对象在整个系统中只有一个，因此也可以使用单例模式。
 *
 * 当客户端需要单纯享元对象的时候，需要调用享元工厂的factory()方法，并传入所需的单纯享元对象的内蕴状态，由工厂方法产生所需要的享元对象。
 */
class FlyweightFactory{
    private Map<Character,Flyweight> files = new HashMap<Character, Flyweight>();

    public Flyweight factory(Character state){
        // 先从缓存中查找对象
        Flyweight fly = files.get(state);
        if(fly==null){
            fly = new ConcreteFlyweight(state);
            files.put(state,fly);
        }
        return fly;
    }
}

class Client3{
    public static void main(String[] args) {

        FlyweightFactory factory = new FlyweightFactory();
        // chart 类型数据是使用单引号的
        Flyweight fly = factory.factory(new Character('a'));
        fly.operation("first call");

        fly = factory.factory(new Character('b'));
        fly.operation("second call");

        fly = factory.factory(new Character('a'));
        fly.operation("third call");
    }

    /**
     * 虽然客户端申请了三个享元对象，但是实际创建的享元对象只有两个，这就是共享的含义。
     */
}