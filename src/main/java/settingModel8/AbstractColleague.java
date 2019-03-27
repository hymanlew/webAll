package settingModel8;

/**
 * 中介者模式（Mediator）：又称为调停者模式，是用一个中介者对象封装一系列的对象交互，中介者使各对象不需要显示地相互作用，从而使耦合松散，
 * 而且可以独立地改变它们之间的交互，行为类模式。
 *
 * 为什么要使用中介者模式：一般来说，同事类之间的关系是比较复杂的，多个同事类之间互相关联时，他们之间的关系会呈现为复杂的网状结构，这是一
 * 种过度耦合的架构，即不利于类的复用，也不稳定。
 * 如果引入中介者模式，那么同事类之间的关系将变为星型结构，从图中可以看到，任何一个类的变动，只会影响的类本身，以及中介者，这样就减小了系统
 * 的耦合。一个好的设计，必定不会把所有的对象关系处理逻辑封装在本类中，而是使用一个专门的类来管理那些不属于自己的行为。
 *
 * 我们使用一个例子来说明一下什么是同事类：有两个类A和B，类中各有一个数字，并且要保证类B中的数字永远是类A中数字的100倍。也就是说，当修改类
 * A的数时，将这个数字乘以100赋给类B，而修改类B时，要将数除以100赋给类A。类A类B互相影响，就称为同事类。代码如下：
 */
public abstract class AbstractColleague {
    protected int number;

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    //抽象方法，修改数字时同时修改关联对象
    public abstract void setNumber(int number,AbstractColleague coll);
}

// 同事A
class ColleagueA extends AbstractColleague{
    @Override
    public void setNumber(int number, AbstractColleague coll) {
        this.number = number;
        coll.setNumber(number*100);
    }
}

// 同事B
class ColleagueB extends AbstractColleague{
    @Override
    public void setNumber(int number, AbstractColleague coll) {
        this.number = number;
        coll.setNumber(number/100);
    }
}

class Client1{
    public static void main(String[] args) {
        AbstractColleague colLa = new ColleagueA();
        AbstractColleague collb = new ColleagueB();

        System.out.println("==========设置A影响B==========");
        colLa.setNumber(1288,collb);
        System.out.println("collA的number值："+colLa.getNumber());
        System.out.println("collB的number值："+collb.getNumber());

        System.out.println("==========设置B影响A==========");
        collb.setNumber(87635,colLa);
        System.out.println("collA的number值："+colLa.getNumber());
        System.out.println("collB的number值："+collb.getNumber());
    }
}

/**
 * 上面的代码中，类A类B通过直接的关联发生关系，假如我们要使用中介者模式，类A类B之间则不可以直接关联，他们之间必须要通过一个中介者来
 * 达到关联的目的。
 */
