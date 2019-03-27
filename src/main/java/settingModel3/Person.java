package settingModel3;

/**
 * 装饰者模式（decorator pattern）：在不必改变原类文件和原类使用的继承的情况下，动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是用装饰来包裹真实的对象来实现。
 *
 * 公共接口：
 */
public interface Person {
    void eat();
}

// 被装饰对象：
class OldPerson implements Person{

    public void eat() {
        System.out.println("吃饭");
    }
}

// 装饰对象：
class NewPerson implements Person{

    private OldPerson p;

    public NewPerson(OldPerson p){
        this.p = p;
    }
    public void eat() {
        System.out.println("生火");
        System.out.println("做饭");
        p.eat();
        System.out.println("刷碗");
    }
}


/**
 * 通过例子可以看到，没有改变原来的OldPerson类，同时也没有定义他的子类而实现了Person的扩展，这就是装饰者模式的作用。
 */
class Test2{
    public static void main(String[] args) {
        OldPerson oldPerson = new OldPerson();
        oldPerson.eat();
        NewPerson newPerson = new NewPerson(oldPerson);
        newPerson.eat();
    }
}