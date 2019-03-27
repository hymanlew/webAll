package settingModel9;

/**
 * 状态模式（state）： 当系统中某个对象存在多个状态，这些状态之间可以进行转换，而且对象在不同状态下行为不相同时可以使用状态模式。状态模式
 * 将一个对象的状态从该对象中分离出来，封装到专门的状态类中，使得对象状态可以灵活变化。状态模式是一种对象行为型模式。
 *
 * 适用场景：用于解决系统中复杂对象的多种状态转换以及不同状态下行为的封装问题。简单说就是处理对象的多种状态及其相互转换。
 */

// 抽象状态类
public abstract class State {
    // 状态行为抽象方法,由具体的状态子类去实现不同的行为逻辑，行为，举止
    public abstract void Behavior();
}

class ConcreteStateA extends State{

    @Override
    public void Behavior() {
        // 状态A 的业务行为, 及当为该状态下时，能干什么。例如：
        System.out.println("手机在未欠费停机状态下, 能正常拨打电话");
    }
}

class ConcreteStateB extends State{
    @Override
    public void Behavior() {
        // 状态B 的业务行为, 及当为该状态下时，能干什么。例如：
        System.out.println("手机在欠费停机状态下, 不能拨打电话");
    }
}

// 拥有状态对象的环境类，环境上下文类，拥有状态对象，且可以完成状态间的转换 [状态的改变/切换 在环境类中实现]
class Context{
    // 维护一个抽象状态对象的引用
    private State state;

    /*
     * 模拟手机的话费属性，环境状态如下：
     * 1>、当  bill >= 0.00$ : 状态正常   还能拨打电话
     * 2>、当  bill < 0.00$ : 手机欠费   不能拨打电话
     */
    private double bill;

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public State getState() {
        return state;
    }

    // 设置环境状态，私有方法，目的是让环境的状态由系统环境自身来控制/切换,外部使用者无需关心环境内部的状态
    private void setState(State state) {
        this.state = state;
    }

    // 在状态模式中，关于状态的切换有两种不同的实现方式：

    // 方式一：检查环境状态:状态的改变/切换 在环境类中实现
    private void checkState(){
        if(bill>=0.0){
            setState(new ConcreteStateA());
        }else {
            setState(new ConcreteStateB());
        }
    }

    /**
     * 环境处理函数，调用状态实例行为 完成业务逻辑
     * 根据不同的状态实例引用  在不同状态下处理不同的行为
     */
    public void Handle(){
        checkState();
        state.Behavior();
    }
}

class Client4{
    public static void main(String[] args) {
        Context context = new Context();
        context.setBill(5.5);
        System.out.println("当前话费余额：" + context.getBill() + "$");
        context.Handle();

        context.setBill(-1.5);
        System.out.println("当前话费余额：" + context.getBill() + "$");
        context.Handle();

        context.setBill(50.0);
        System.out.println("当前话费余额：" + context.getBill() + "$");
        context.Handle();
    }
}



// 方式二：在环境类Context类中初始化一个状态实例对象，并将环境 Context对象作为子类状态的构造参数传递到具体的状态子类实例中。
class Context2{
    private State state;

    private double bill;

    public void setState(State state) {
        this.state = new ConcreteStateA2(this);
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}

// 然后在具体的子类状态类中根据构造进来的context对象，通过调用context对象的属性值进行业务逻辑判断 进行状态的检查和切换。
class ConcreteStateA2 extends State{
    private Context2 context2;

    public ConcreteStateA2(Context2 context2) {
        context2 = context2;
    }

    @Override
    public void Behavior() {
        // 状态A 的业务行为, 及当为该状态下时，能干什么
        // 如：手机在未欠费停机状态下, 能正常拨打电话
        System.out.println("手机在未欠费停机状态下, 能正常拨打电话");
        checkState();
    }

    private void checkState(){
        if(context2.getBill()<0.0){
            context2.setState(new ConcreteStateB());
        }
    }
}
