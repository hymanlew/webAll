package settingModel5;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式（Observer）：
 * 对于普通的观察者模式，当是对已经被封装的方法或功能进行通知操作时（例如对本开发软件点击运行按钮，窗口所发生的变化进行改动时），
 * 是不可能用接口的方式来实现观察者模式的（已经被制造商封装了）。
 * 即普通的观察者模式尽管已经用了依赖倒置原则，但 ’抽象通知者 Subject‘ 还是依赖 ’抽象观察者 ObserverDemo‘，即万一没有了抽象观
 * 察者这样的接口，则通知的功能就完不成了。另外每个具体的观察者，也不一定都是对同一方法的调用（就像本软件窗口的变化，有关闭有打
 * 开，这就不是同一个方法）。
 * 所以就要采取通知者与观察者之间互不知道，由客户端来决定通知谁，使用委托的模式。
 * 委托机制的实现不再需要提取观察者抽象类，观察者和通知者互不依赖。java利用反射即可实现
 *
 * 委托，就是一种引用方法的类型。一旦为委托分配了方法，委托将与该方法具有完全相同的行为。委托方法的使用可以像其他任何方法一样，
 * 具有参数和返回值。委托可以看作是对函数的抽象，是函数的类，委托的实例将代表一个具体的函数。
 *
 * 委托是指在一个类中包含了其它的类或者接口，通过这借助多态，实现一些动态行为，委托只能算是设计模式中的一部分，代理中有用
 * 到委托的，在策略模式中也可能会用到。但是它是有前提的：就是委托对象所搭载的所有方法必须具有相同的原形和形式，也就是拥有相同的
 * 参数列表和返回值类型。
 * 委托模式在jvm类加载器中有用到，委托不等于代理。一个委托可以搭载多个方法，所有方法被依次唤起，并且它可以使得委托对旬所搭载的方
 * 法并不需要属于同一个类。
 */

// 被观察者的总类，通知者，由于它不希望依赖 ’观察者‘，所以增加减少观察者的方法也就没必要了
public abstract class Subject2 {
    private EventHandler handler = new EventHandler();

    abstract String getName();
    abstract void notifyObserver();
    public void addListener(Object object,String methodName,Object...args){
        notifyObserver();
        handler.addEvent(object,methodName,args);
    };
    public void doNotify(){
        try {
            handler.notifyX();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public EventHandler getHandler() {
        return handler;
    }

    public void setHandler(EventHandler handler) {
        this.handler = handler;
    }
}


// 具体观察者去掉了观察者总接口，然后声明各自的操作方法
class ConcreteObserver21 {
    private String name;
    private Subject2 sub;

    public ConcreteObserver21(String name,Subject2 sub){
        System.out.println("观察者1 ===");
        this.name = name;
        this.sub = sub;
    }
    public void update21(){
        System.out.println("观察者1收到信息，并进行处理。"+sub.getName());
    };
}

class ConcreteObserver22 {
    private String name;
    private Subject2 sub;

    public ConcreteObserver22(String name,Subject2 sub){
        System.out.println("观察者2 ===");
        this.name = name;
        this.sub = sub;
    }
    public void update22(){
        System.out.println("观察者2收到信息，并进行处理。"+sub.getName());
    };
}


class Event {
    //要执行方法的对象
    private Object object;
    //要执行的方法名称
    private String methodName;
    //要执行方法的参数
    private Object[] params;
    //要执行方法的参数类型
    private Class[] paramTypes;

    public Event(){

    }
    public Event(Object object,String methodName,Object...args){
        this.object=object;
        this.methodName=methodName;
        this.params=args;
        contractParamTypes(this.params);
    }

    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public Object[] getParams() {
        return params;
    }
    public void setParams(Object[] params) {
        this.params = params;
    }
    public Class[] getParamTypes() {
        return paramTypes;
    }

    //根据参数数组生成参数类型数组
    private void contractParamTypes(Object[] params){
        if(params==null || params.length==0){
            return;
        }
        this.paramTypes=new Class[params.length];
        for(int i=0;i<params.length;i++){
            this.paramTypes[i]=params[i].getClass();
        }
    }

    public Object getObject() {
        return object;
    }
    //若干setter getter省略
    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }
    //执行该 对象的该方法
    public void invoke() throws Exception{
        Method method=object.getClass().getMethod(this.getMethodName(), this.getParamTypes());
        if(null==method){
            return;
        }
        method.invoke(this.getObject(), this.getParams());
    }
}

// 声明一个委托，叫事件处理程序，无参，无返回值
class EventHandler {
    //是用一个List
    private List<Event> objects;

    public EventHandler(){
        objects=new ArrayList<Event>();
    }
    //添加某个对象要执行的事件，及需要的参数
    public void addEvent(Object object,String methodName,Object...args){
        objects.add(new Event(object,methodName,args));
    }
    //通知所有的对象执行指定的事件
    public void notifyX() throws Exception{
        for(Event e : objects){
            e.invoke();
        }
    }
}

// 通知者的实现
class ConcreteSubject2 extends Subject2{
    public String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ConcreteSubject2(String name){
        this.name = name;
    }
    @Override
    protected void notifyObserver() {
        System.out.println("通知者发出通知 ===");
    }
}

class ClientTe22{
    public static void main(String[] args) {

        Subject2 subject = new ConcreteSubject2("通知者");
        ConcreteObserver21 con1 = new ConcreteObserver21("观察者1",subject);   //添加观察者1
        ConcreteObserver22 con2 = new ConcreteObserver22("观察者2",subject);   //添加观察者2

        subject.addListener(con1,"update21",null);
        subject.addListener(con2,"update22",null);

        subject.doNotify();
    }
}