package settingModel6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 访问者模式（Visitor ）：封装某些作用于某种数据结构中各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新的操作，行为类模式。
 */
interface Ivisitor{
    public void visit(ConcreteElement1 e1);
    public void visit(ConcreteElement2 e2);
}


public abstract class Element {
    public abstract void accept(Ivisitor ivisitor);
    public abstract void dosomething();
}


class ConcreteElement1 extends Element{
    @Override
    public void accept(Ivisitor ivisitor) {
        ivisitor.visit(this);
    }

    @Override
    public void dosomething() {
        System.out.println("这是元素1");
    }
}


class ConcreteElement2 extends Element{
    @Override
    public void accept(Ivisitor ivisitor) {
        ivisitor.visit(this);
    }

    @Override
    public void dosomething() {
        System.out.println("这是元素2");
    }
}


class Visitor implements Ivisitor{

    public void visit(ConcreteElement1 e1) {
        e1.dosomething();
    }

    public void visit(ConcreteElement2 e2) {
        e2.dosomething();
    }
}


class ObjectStruture{
    public static List<Element> getList(){
        List<Element> list = new ArrayList<Element>();
        Random random = new Random();
        for(int i=0;i<10;i++){
            int a = random.nextInt(100);
            if(a>50){
                list.add(new ConcreteElement1());
            }else {
                list.add(new ConcreteElement2());
            }
        }
        return list;
    }
}


class Client2{
    public static void main(String[] args) {
        List<Element> list = ObjectStruture.getList();
        for(Element e:list){
            e.accept(new Visitor());
        }
    }
}