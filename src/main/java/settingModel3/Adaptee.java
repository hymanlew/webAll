package settingModel3;

/**
 * 适配器模式（adaptor pattern）：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 *
 * 分类：
 *   1，类适配器模式
 */
public class Adaptee {
    public void specRequest(){
        System.out.println("特殊请求，这个是源角色接口");
    }
}

/**
 * 这个是目标角色，所期待的接口
 */
interface Target{
    public void request();
}

/**
 * 现在想要实现这个Target接口，但是不想重构，想要用上已有的Adaptee类，这时可以定义一个适配器类，继承想要使用的类，并且实现期待的接口。
 */
class Adapter extends Adaptee implements Target{

    //原始请求然后适配处理
    public void request() {
        super.specRequest();
    }
}

/**
 * 这样，使用适配器类和实现目标接口就完成了计划，测试：
 */
class Test{
    public static void main(String[] args) {
        //使用特殊功能类，即适配类
        Target adapter = new Adapter();
        adapter.request();
    }
}


/**
 * 分类：
 *   2，对象适配器模式：适配器类关联已有的Adaptee类，并且实现标准接口，这样做的好处是不再需要继承。
 */
class Adapter1 implements Target{

    private Adaptee adaptee;

    public Adapter1(Adaptee adaptee){
        this.adaptee = adaptee;
    }
    public void request() {
        this.adaptee.specRequest();
    }
}

/**
 * 我们可以想到，此时输出结果和类适配器模式是相同的，测试：
 */
class Test1{
    public static void main(String[] args) {
        //使用特殊功能类，即适配类
        Target adapter = new Adapter1(new Adaptee());
        adapter.request();
    }
}