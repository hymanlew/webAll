package settingModel4;

/**
 * 桥接模式（Bridge）：将抽象部分与实现部分分离，使它们都可以独立的变化。桥接模式是一种结构式模式。
 *
 * 定义实现接口：
 */
public interface Implementor {
    // 实现抽象部分需要的某些具体功能
    public void operationImpl();
}


// 定义抽象接口
abstract class Abstraction{
    // 持有一个 Implementor 对象，形成聚合关系
    protected Implementor implementor;

    public Abstraction(Implementor implementor){
        this.implementor = implementor;
    }

    // 可能需要转调实现部分的具体实现
    public void operation(){
        implementor.operationImpl();
    }
}


// 实现 Implementor 中定义的接口。
class ConcreteImplementorA implements Implementor{

    public void operationImpl() {
        // 真正的实现
        System.out.println("具体实现A");
    }
}

class ConcreteImplementorB implements Implementor{

    public void operationImpl() {
        // 真正的实现
        System.out.println("具体实现B");
    }
}


//扩展 Abstraction 类
class RefineAbstraction extends Abstraction{

    public RefineAbstraction(Implementor implementor) {
        super(implementor);
    }

    public void otherOperation(){
        // 实现一定的功能，可能会使用具体实现部分的实现方法,
        // 但是本方法更大的可能是使用 Abstraction 中定义的方法，
        // 通过组合使用 Abstraction 中定义的方法来完成更多的功能
        System.out.println("扩展实现...");
    }
}

//TEST
class BridgePattern{
    public static void main(String[] args) {
        Implementor implementor = new ConcreteImplementorA();
        RefineAbstraction abstraction = new RefineAbstraction(implementor);
        abstraction.operation();
        abstraction.otherOperation();
    }
}