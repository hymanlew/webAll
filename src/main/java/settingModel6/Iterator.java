package settingModel6;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器模式（Iterator）：提供一种方法访问一个容器对象中各个元素，而又不暴露该对象的内部细节。是行为类模式。它是 java 中使用最多的一种模式。
 *
 * java语言已经完整地实现了迭代器模式，Iterator翻译成汉语就是迭代器的意思。提到迭代器，首先它是与集合相关的，集合也叫聚集、容器等，我们可以
 * 将集合看成是一个可以包容对象的容器，例如List，Set，Map，甚至数组都可以叫做集合，而迭代器的作用就是把容器中的对象一个一个地遍历出来。
 */
// 重写 Iterator 接口，实现 demo 功能
public interface Iterator {
    public Object next();
    public boolean hasNext();
}

class ConcreteIterator implements Iterator{

    private List list;
    private int cursor = 0;

    public ConcreteIterator(List list){
        this.list = list;
    }

    public Object next() {
        Object object = null;
        if (this.hasNext()) {
            object = this.list.get(cursor++);
        }
        return object;
    }

    public boolean hasNext() {
        if(cursor == this.list.size()){
            return false;
        }
        return true;
    }
}

// 集合；聚集
interface Aggregate{
    public void add(Object object);
    public void remove(Object object);
    public Iterator iterator();
}

class ConcreteAggregate implements Aggregate{

    private List list = new ArrayList();

    public void add(Object object) {
        list.add(object);
    }

    public void remove(Object object) {
        list.remove(object);
    }

    public Iterator iterator() {
        return new ConcreteIterator(list);
    }
}

class Client{
    public static void main(String[] args) {

        Aggregate ag = new ConcreteAggregate();
        ag.add("a");
        ag.add("b");
        ag.add("c");
        ag.add("d");
        Iterator iterator = ag.iterator();
        while (iterator.hasNext()){
            String s = (String)iterator.next();
            System.out.println(s);
        }
    }
}