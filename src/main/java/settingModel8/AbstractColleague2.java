package settingModel8;

// 使用中介者模式，重构代码
public abstract class AbstractColleague2 {
    protected int number;

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    //注意这里的参数不再是同事类，而是一个中介者
    public abstract void setNumber(int number,AbstractMediator am);
}

// 中介者
abstract class AbstractMediator{
    protected AbstractColleague2 A;
    protected AbstractColleague2 B;

    public AbstractMediator( AbstractColleague2 a,AbstractColleague2 b){
        A = a;
        B = b;
    }

    public abstract void AaffectB();

    public abstract void BaffectA();
}

// 同事A
class ColleagueA2 extends AbstractColleague2{
    @Override
    public void setNumber(int number, AbstractMediator am) {
        this.number = number;
        am.AaffectB();
    }
}

// 同事B
class ColleagueB2 extends AbstractColleague2{
    @Override
    public void setNumber(int number, AbstractMediator am) {
        this.number = number;
        am.BaffectA();
    }
}

class Mediator extends AbstractMediator{
    public Mediator(AbstractColleague2 a,AbstractColleague2 b){
        super(a,b);
    }

    @Override
    public void AaffectB() {
        int number = A.getNumber();
        B.setNumber(number*100);
    }

    @Override
    public void BaffectA() {
        int number = B.getNumber();
        A.setNumber(number/100);
    }
}

class Client2{
    public static void main(String[] args) {
        AbstractColleague2 colLa = new ColleagueA2();
        AbstractColleague2 collb = new ColleagueB2();

        AbstractMediator am = new Mediator(colLa,collb);

        System.out.println("==========设置A影响B==========");
        colLa.setNumber(1000,am);
        System.out.println("collA的number值："+colLa.getNumber());
        System.out.println("collB的number值："+collb.getNumber());

        System.out.println("==========设置B影响A==========");
        collb.setNumber(1000,am);
        System.out.println("collA的number值："+colLa.getNumber());
        System.out.println("collB的number值："+collb.getNumber());
    }
}

/**
 * 虽然代码比较长，但是还是比较容易理解的，其实就是把原来处理对象关系的代码重新封装到一个中介类中，通过这个中介类来处理对象间的关系。
 */
