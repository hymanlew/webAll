package settingModel9;

import settingModel.Singleton;

public class ModelDetail {
    /**
     *
     *  将类与类之间的关系，用聚合代替继承，即将相关联可能发生关系的不同的类，作为其中一个类的成员变量，而不是继承关系或者
     *  实现的关系。也可以用于不同系列类之间的关系。
     *
     *
     * settingModel：
     *
     *      Singleton        ：单例模式（Singleton）
     *      car，IcarFactory ：工厂方法模式（factory）
     *      LineTest，task   ： volatile
     *
     * settingModel2：
     *
     *      Mail            ：原型模式（Prototype）
     *      Product，IBuild ：生成器模式（builder pattern）
     *
     * settingModel3：
     *
     *      Adaptee         ：适配器模式（adaptor pattern）
     *      Person          ：装饰者模式（decorator pattern）
     *      UserInfo        ：代理模式（Proxy pattern）
     *
     * settingModel4：
     *
     *      FolderComponent ：组合模式（Composite）
     *      Implementor     ：桥接模式（Bridge）
     *      SubClass1       ：外观模式（Facade）
     *
     * settingModel5：
     *
     *      AbstractSort    ：模版方法模式（Template method pattern）
     *      IStrategy       ：策略模式（Strategy Pattern）
     *      Printf          ：System.out.printf（"%n"，n）
     *      Subject         ：观察者模式（Observer）
     *      Subject2        ：委托模式（）
     *
     * settingModel6：
     *
     *      Element         ：访问者模式（Visitor ）
     *      Expression/1    ：解释器模式（interpreter）
     *      Iterator        ：迭代器模式（Iterator）
     *
     * settingModel7：
     *
     *      BeanInfoDemo    ：BeanInfo
     *      BeanTranstoMap  ：Bean、Map转换
     *      Command         ：命令模式（Command pattern）
     *      Memento/1       ：备忘录模式（Memento）
     *
     * settingModel8：
     *
     *      AbstraCollegue/2：中介者模式（Mediator）
     *      Flyweight/2     ：享元模式（Flyweight）
     *      Request         ：责任链模式（Chain of Responsibility）
     *
     * settingModel9：
     *
     *      State           ：状态模式（state）
     *      Emplyee         ：迪米特法则
     *
     *
     *
     * 23三种设计模式：
     * 创建型模式：单例模式、抽象工厂模式、建造者模式、工厂模式、原型模式。
     * 结构型模式：适配器模式、桥接模式、装饰模式、组合模式、外观模式、享元模式、代理模式。
     * 行为型模式：模版方法模式、命令模式、迭代器模式、观察者模式、中介者模式、备忘录模式、解释器模式、状态模式、策略模式、职
     *             责链模式、访问者模式。
     *
     * 委托是指在一个类中包含了其它的类或者接口，通过这借助多态，实现一些动态行为，委托只能算是设计模式中的一部分，代理中有用
     * 到委托的，在策略模式中也可能会用到。
     * 委托模式在jvm类加载器中有用到，委托不等于代理。
     */



    public static void main(String[] args) {
        Singleton.getInstance();
    }
}
