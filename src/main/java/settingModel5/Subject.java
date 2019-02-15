package settingModel5;

import java.util.Observer;
import java.util.Vector;

/**
 * 观察者模式（Observer）：定义对象间一种一对多的依赖关系，使得当每一个对象改变状态，则所有依赖于它的对象都会得到通知并自动更新。是行为类模式。
 * 就是一个对象要时刻监听着另一个对象，只要它的状态一发生改变，自己随之要做出相应的行动。其实，能够实现这一点的方案很多，但是，无疑使用观察者模
 * 式是一个主流的选择。
 *
 * 对于观察者模式，其实Java已经为我们提供了已有的接口和类：Observer 和 Observable（两者不是实现的关系，但接口中有调用观察者类的抽象方法）。
 *
 * Vector 类可以实现可增长的对象数组。与数组一样，它包含可以使用整数索引进行访问的组件。但是，Vector 的大小可以根据需要增大或缩小，以适应创
 * 建 Vector 后进行添加或移除项的操作。
 */

// 被观察者的总类
public abstract class Subject {
    private Vector<ObserverDemo> obs = new Vector<ObserverDemo>();

    public void addObserver(ObserverDemo obs){
        this.obs.add(obs);
    }

    public void delObserver(Observer obs){
        this.obs.remove(obs);
    }

    protected void notifyObserver(){
        for(ObserverDemo o:obs){
            o.update();
        }
    }

    public abstract void doSomething();
}


// 为了简单在实现 demo 功能，先模拟一个 observer 观察者接口即（ObserverDemo），并实现
interface ObserverDemo{
    public void update();
}

class ConcreteObserver1 implements ObserverDemo{
    public void update(){
        System.out.println("观察者1收到信息，并进行处理。");
    };
}

class ConcreteObserver2 implements ObserverDemo{
    public void update(){
        System.out.println("观察者2收到信息，并进行处理。");
    };
}

// 被观察者的实现
class ConcreteSubject extends Subject{
    @Override
    public void doSomething() {
        System.out.println("被观察者事件反生");
        this.notifyObserver();
    }
}

class ClientTe2{
    public static void main(String[] args) {

        Subject subject = new ConcreteSubject();
        subject.addObserver(new ConcreteObserver1());   //添加观察者1
        subject.addObserver(new ConcreteObserver2());   //添加观察者2
        subject.doSomething();
    }
}