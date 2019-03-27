package settingModel5;

/**
 * 策略模式（Strategy Pattern）：定义一组算法，将每个算法都封装起来，并且使他们之间可以互换。也是一种行为类模式。
 *
 * 策略模式是对算法的封装，把一系列的算法分别封装到对应的类中，并且这些类实现相同的接口，相互之间可以替换。在模版方法模式的行为类模式中，也是关
 * 注对算法的封装。
 * 策略模式与模版方法模式的区别仅仅是多了一个单独的封装类Context，它与模版方法模式的区别在于：在模版方法模式中，调用算法的主体在抽象的父类中，
 * 而在策略模式中，调用算法的主体则是封装到了封装类Context中，抽象策略Strategy一般是一个接口，目的只是为了定义规范，里面一般不包含逻辑。
 *
 * 其实这只是通用实现，而在实际编程中，因为各个具体策略实现类之间难免存在一些相同的逻辑，为了避免重复的代码，我们常常使用抽象类来担任Strategy
 * 的角色，在里面封装公共的代码，因此，在很多应用的场景中，在策略模式中一般会看到模版方法模式的影子。
 */
public interface IStrategy {
    public void doSomething();
}

class ConcreteStrategy1 implements IStrategy{
    public void doSomething() {
        System.out.println("具体策略1");
    }
}

class ConcreteStrategy2 implements IStrategy{
    public void doSomething() {
        System.out.println("具体策略2");
    }
}

class Context{
    private IStrategy strategy;

    public Context(IStrategy strategy){
        this.strategy = strategy;
    }

    public void execute(){
        strategy.doSomething();
    }
}

class ClientTe{
    public static void main(String[] args) {

        Context context;
        System.out.println("-----执行策略1-----");
        context = new Context(new ConcreteStrategy1());
        context.execute();

        System.out.println("-----执行策略2-----");
        context = new Context(new ConcreteStrategy2());
        context.execute();
    }
}